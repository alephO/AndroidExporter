package me.aleph0.androidexporter;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;

public class ExportAction extends AnAction {
    public ExportAction(){
        super("_Export for submission");
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);
        MyDialogWrapper dialogWrapper = new MyDialogWrapper(project);
        dialogWrapper.show();
    }
}
