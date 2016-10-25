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

//        VirtualFile virtualFile = FileChooser.chooseFile(new FileChooserDescriptor(true,true,false,false,false,false),null,null);
//        String path = project.getBasePath();
//        String[] choose = {"c1","c2"};
//        Messages.showEditableChooseDialog(project.getPresentableUrl(),virtualFile.getPath(),null,choose,choose[0],null);
//        //String txt= Messages.showInputDialog(project, "What is your name?", "Input your name", Messages.getQuestionIcon());
//        int res = Messages.showOkCancelDialog(project, "You must save your project before you process. Save now?", "wait!","Save","Cancel",Messages.getQuestionIcon());
//        if(res == Messages.OK){
//            project.save();
//        }else{
//            return;
//        }
        //Messages.showMessageDialog(project, "Hello, " + s1 + " " + txt + "!\n I am glad to see you.", "Information", Messages.getInformationIcon());

    }
}
