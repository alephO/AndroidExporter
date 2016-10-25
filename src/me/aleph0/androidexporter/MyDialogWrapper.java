package me.aleph0.androidexporter;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MyDialogWrapper extends DialogWrapper {
    private Project project;
    private JPanel panel = new JPanel();
    {
        panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
    }
    private JLabel label1 = new JLabel("This tool will save and compress the following project:");
    private JLabel label2;
    private JLabel label3 = new JLabel("Please set the zip file name and location:");
    private JLabel label4 = new JLabel("");
    private JTextField filefield = new JTextField();

    private TextFieldWithBrowseButton textFieldWithBrowseButton;

    public MyDialogWrapper(Project project){
        super(project);
        super.init();
        //panel.setSize(800,600);
        this.project = project;
        label2= new JLabel(project.getName());
        textFieldWithBrowseButton = new TextFieldWithBrowseButton(filefield,
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        VirtualFile file = FileChooser.chooseFile(
                                new FileChooserDescriptor(true,true,true,false,false,false),
                                project,
                                null
                        );
                        if(file == null) return;
                        if(file.getExtension()!=null&&file.getExtension().equals("zip")){
                            filefield.setText(file.getPresentableUrl());
                        } else if(file.isDirectory()){
                            filefield.setText(file.getPresentableUrl()+ File.separator + project.getName()+".zip");
                        }
                    }
                }
        );
        filefield.setColumns(30);
        label1.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label1);
        label2.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label2);
        label3.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label3);
        textFieldWithBrowseButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(textFieldWithBrowseButton);
        Font curfont = label4.getFont();
        label4.setFont(new Font(curfont.getName(),Font.BOLD,curfont.getSize()));
        label4.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label4);
        setTitle("Homework Exporter");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return panel;
    }

    @Override
    protected void doOKAction() {
        if(getOKAction().isEnabled()) {
            project.save();
            if (filefield.getText() != null) {
                getOKAction().setEnabled(false);
                label4.setText("Processing...Please wait");
                ZipUtil zipUtil= new ZipUtil(project.getBasePath(),filefield.getText());
                if(zipUtil.zip()){
                    label4.setText("");
                    Messages.showInfoMessage(project,"Done!","Info");
                    close(OK_EXIT_CODE);
                } else {
                    getOKAction().setEnabled(true);
                    label4.setText("");
                    Messages.showErrorDialog(project,"Something went wrong...","Oops");
                }
            }
        }
    }


}
