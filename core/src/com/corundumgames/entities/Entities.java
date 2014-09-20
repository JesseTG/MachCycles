package com.corundumgames.entities;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.EntityEdit;
import com.artemis.World;
import com.artemis.managers.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.corundumgames.Assets;

/**
 * The dominating class of this package
 * 
 * @author jesse
 * 
 */
public final class Entities {
    private static World world;
    private static AssetManager assets;

    public static void setEntityWorld(final World world) {
        Entities.world = world;
    }

    public static void setAssetManager(AssetManager assets) {
        Entities.assets = assets;
    }

    /** No touchey */
    private Entities() {}

    public static Entity createEntityFromFile(FileHandle file) {
        return create_entity(file, true);
    }

    public static Entity createEntityFromFile(String path) {
        return create_entity(Gdx.files.internal(path), true);
    }

    private static Entity create_entity(FileHandle file, boolean activate) {
        // We need this activate flag because we're creating parent entities and
        // turning them into their children, but don't want to add the same
        // entity multiple times
        String filename = file.toString();
        final Entity entity;
        if (!assets.isLoaded(filename)) {
            assets.load(filename, JsonValue.class, Assets.JSON_PARAMETER);
            assets.finishLoading();
        }

        JsonValue data = assets.get(filename);
        JsonValue config = data.get("config");
        JsonValue components = data.getChild("components");
        JsonValue children = null;
        String parent = data.getString("extends", null);
        if (parent == null) {
            entity = world.createEntity();
        }
        else {
            entity = create_entity(Gdx.files.internal(getEntityPath(parent)), false);
        }
        final EntityEdit edit = entity.edit();

        Json json = new Json()
        {
            @SuppressWarnings("unchecked")
            @Override
            protected Object newInstance(Class cls) {
                if (ClassReflection.isAssignableFrom(Component.class, cls)) {
                    return edit.create(cls);
                }
                else {
                    return super.newInstance(cls);
                }
            }
        };

        if (config != null) {
            String player = config.getString("player", null);
            if (player != null) {
                world.getManager(PlayerManager.class).setPlayer(entity, player);

                String team = config.getString("team", null);
                if (team != null) {
                    world.getManager(TeamManager.class).setTeam(player, team);
                }
            }

            JsonValue tags = config.get("tags");
            if (tags != null && tags.isArray()) {
                String[] t = tags.asStringArray();
                TagManager tagmanager = world.getManager(TagManager.class);
                for (final String tag : t) {
                    tagmanager.register(tag, entity);
                }
            }

            GroupManager groupmanager = world.getManager(GroupManager.class);
            groupmanager.add(entity, file.nameWithoutExtension());
            JsonValue groups = config.get("groups");
            if (groups != null && groups.isArray()) {
                String[] g = groups.asStringArray();

                for (final String group : g) {
                    groupmanager.add(entity, group);
                }
            }

            children = config.get("children");
            // Not used right now
        }

        if (components != null) {
            for (JsonValue j = components; j != null; j = j.next()) {
                try {
                    Class<? extends Component> cls = ClassReflection.forName("com.corundumgames.gravv.components."
                            + j.name);
                    json.fromJson(cls, j.toString());
                }
                catch (Exception e) {
                    Gdx.app.error("Entity", "Couldn't create " + j.name + " for entity " + file, e);
                }
            }
        }
        if (activate) {
            Gdx.app.debug("Entity", "Created entity " + file.nameWithoutExtension());
        }

        /*
         * if (children != null && children.isArray()) { for (JsonValue j :
         * children) { if (j.isString()) { Entity e =
         * createEntityFromFile(getEntityPath(j.asString()));
         * child.setChild(entity, e); } } } // Might re-enable this if entities
         * should have child entities
         */

        return entity;
    }

    public static String getEntityPath(String name) {
        return "entity/" + name + ".json";
    }
}
