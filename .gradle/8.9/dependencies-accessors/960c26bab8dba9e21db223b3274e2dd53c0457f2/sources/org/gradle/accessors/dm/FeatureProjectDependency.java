package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.internal.artifacts.dependencies.ProjectDependencyInternal;
import org.gradle.api.internal.artifacts.DefaultProjectDependencyFactory;
import org.gradle.api.internal.artifacts.dsl.dependencies.ProjectFinder;
import org.gradle.api.internal.catalog.DelegatingProjectDependency;
import org.gradle.api.internal.catalog.TypeSafeProjectDependencyFactory;
import javax.inject.Inject;

@NonNullApi
public class FeatureProjectDependency extends DelegatingProjectDependency {

    @Inject
    public FeatureProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":feature:album"
     */
    public Feature_AlbumProjectDependency getAlbum() { return new Feature_AlbumProjectDependency(getFactory(), create(":feature:album")); }

    /**
     * Creates a project dependency on the project at path ":feature:currentplaying"
     */
    public Feature_CurrentplayingProjectDependency getCurrentplaying() { return new Feature_CurrentplayingProjectDependency(getFactory(), create(":feature:currentplaying")); }

    /**
     * Creates a project dependency on the project at path ":feature:library"
     */
    public Feature_LibraryProjectDependency getLibrary() { return new Feature_LibraryProjectDependency(getFactory(), create(":feature:library")); }

    /**
     * Creates a project dependency on the project at path ":feature:miniplayer"
     */
    public Feature_MiniplayerProjectDependency getMiniplayer() { return new Feature_MiniplayerProjectDependency(getFactory(), create(":feature:miniplayer")); }

}
