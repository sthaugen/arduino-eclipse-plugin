package it.baeyens.arduino.listeners;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.cdt.core.index.IIndexChangeEvent;
import org.eclipse.cdt.core.index.IIndexChangeListener;
import org.eclipse.cdt.core.index.IIndexerStateEvent;
import org.eclipse.cdt.core.index.IIndexerStateListener;
import org.eclipse.core.resources.IProject;

import it.baeyens.arduino.common.InstancePreferences;
import it.baeyens.arduino.tools.Libraries;

public class IndexerListener implements IIndexChangeListener, IIndexerStateListener {
    Set<IProject> ChangedProjects = new HashSet<>();

    @Override
    public void indexChanged(IIndexChangeEvent event) {
	ChangedProjects.add(event.getAffectedProject().getProject());

    }

    @Override
    public void indexChanged(IIndexerStateEvent event) {

	if (event.indexerIsIdle()) {
	    if (InstancePreferences.getAutomaticallyIncludeLibraries()) {
		for (IProject curProject : ChangedProjects) {
		    Libraries.checkLibraries(curProject);
		}
	    }
	    ChangedProjects.clear();
	}
    }

}