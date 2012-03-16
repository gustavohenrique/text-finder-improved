package br.com.wdev.plugins;

import hudson.model.BuildBadgeAction;
import hudson.model.AbstractBuild;
import hudson.model.Run;
import br.com.wdev.Finder;

public class TextFinderAction implements BuildBadgeAction {
    
    public AbstractBuild<?,?> build;
    
    private Finder finder;
    
    public TextFinderAction(final Run build, final Finder finder) {
        this.build = (AbstractBuild<?, ?>) build;
        this.finder = finder;
    }
    
    public Finder getFinder() {
        return finder;
    }
    
    public String getBuildUrl() {
        return build.getNumber() + "/" + getUrlName();
    }
    
    public String getIconFileName() {
        return "/plugin/text-finder-improved/images/icon.png";
    }   

    public String getDisplayName() {
        return "Text Finder Report";
    }

    public String getUrlName() {
        return "text-finder-report";
    }

}
