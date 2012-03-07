package br.com.wdev.plugins;

import hudson.model.AbstractProject;

import java.util.List;

import br.com.wdev.Finder;

public class FinderProjectAction extends FinderAction {

    public List<Finder> finders;
    
    public AbstractProject project;

    public FinderProjectAction(AbstractProject project, List<Finder> finders) {
        super(null, null);
        this.project = project;
        this.finders = finders;
    }

}
