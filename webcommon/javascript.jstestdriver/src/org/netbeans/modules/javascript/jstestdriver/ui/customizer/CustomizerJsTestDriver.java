/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.netbeans.modules.javascript.jstestdriver.ui.customizer;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.netbeans.api.project.Project;
import org.netbeans.modules.javascript.jstestdriver.preferences.JsTestDriverPreferences;
import org.netbeans.modules.javascript.jstestdriver.preferences.JsTestDriverPreferencesValidator;
import org.netbeans.modules.javascript.jstestdriver.util.JsTestDriverUtils;
import org.netbeans.modules.web.common.api.ValidationResult;
import org.openide.awt.Mnemonics;
import org.openide.awt.StatusDisplayer;
import org.openide.filesystems.FileChooserBuilder;
import org.openide.util.ChangeSupport;
import org.openide.util.NbBundle;

public final class CustomizerJsTestDriver extends javax.swing.JPanel {

    private final Project project;
    private final ChangeSupport changeSupport = new ChangeSupport(this);

    private volatile String config;

    // @GuardedBy("EDT")
    private ValidationResult validationResult;


    public CustomizerJsTestDriver(Project project) {
        assert EventQueue.isDispatchThread();
        assert project != null;

        this.project = project;

        initComponents();
        init();
    }

    public void addChangeListener(ChangeListener listener) {
        changeSupport.addChangeListener(listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        changeSupport.removeChangeListener(listener);
    }

    public String getConfig() {
        return config;
    }

    public String getWarningMessage() {
        assert EventQueue.isDispatchThread();
        for (ValidationResult.Message message : validationResult.getWarnings()) {
            return message.getMessage();
        }
        return null;
    }

    public String getErrorMessage() {
        assert EventQueue.isDispatchThread();
        for (ValidationResult.Message message : validationResult.getErrors()) {
            return message.getMessage();
        }
        return null;
    }

    private void init() {
        assert EventQueue.isDispatchThread();
        // data
        configTextField.setText(JsTestDriverPreferences.getConfig(project));
        // listeners
        addListeners();
        // initial validation
        validateData();
    }

    private void addListeners() {
        DocumentListener defaultDocumentListener = new DefaultDocumentListener();
        configTextField.getDocument().addDocumentListener(defaultDocumentListener);
    }

    void validateData() {
        assert EventQueue.isDispatchThread();
        config = configTextField.getText();
        validationResult = new JsTestDriverPreferencesValidator()
                .validateConfig(config)
                .getResult();
        changeSupport.fireChange();
    }


    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form
     * Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        configLabel = new JLabel();
        configTextField = new JTextField();
        configBrowseButton = new JButton();
        configSearchButton = new JButton();

        configLabel.setLabelFor(configTextField);
        Mnemonics.setLocalizedText(configLabel, NbBundle.getMessage(CustomizerJsTestDriver.class, "CustomizerJsTestDriver.configLabel.text")); // NOI18N

        Mnemonics.setLocalizedText(configBrowseButton, NbBundle.getMessage(CustomizerJsTestDriver.class, "CustomizerJsTestDriver.configBrowseButton.text")); // NOI18N
        configBrowseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                configBrowseButtonActionPerformed(evt);
            }
        });

        Mnemonics.setLocalizedText(configSearchButton, NbBundle.getMessage(CustomizerJsTestDriver.class, "CustomizerJsTestDriver.configSearchButton.text")); // NOI18N
        configSearchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                configSearchButtonActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(configLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(configTextField)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(configBrowseButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(configSearchButton))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(configLabel)
                .addComponent(configTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(configBrowseButton)
                .addComponent(configSearchButton))
        );
    }// </editor-fold>//GEN-END:initComponents

    @NbBundle.Messages("CustomizerJsTestDriver.chooser.config=Select JS Test Driver configuration file")
    private void configBrowseButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_configBrowseButtonActionPerformed
        assert EventQueue.isDispatchThread();
        File file = new FileChooserBuilder(CustomizerJsTestDriver.class)
                .setTitle(Bundle.CustomizerJsTestDriver_chooser_config())
                .setFilesOnly(true)
                .setDefaultWorkingDirectory(JsTestDriverUtils.getJsTestDriverConfigDir(project))
                .forceUseOfDefaultWorkingDirectory(true)
                .showOpenDialog();
        if (file != null) {
            configTextField.setText(file.getAbsolutePath());
        }
    }//GEN-LAST:event_configBrowseButtonActionPerformed

    @NbBundle.Messages("CustomizerJsTestDriver.config.none=No jsTestDriver.conf was found.")
    private void configSearchButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_configSearchButtonActionPerformed
        assert EventQueue.isDispatchThread();
        File configFile = JsTestDriverUtils.findJsTestDriverConfig(JsTestDriverUtils.getJsTestDriverConfigDir(project));
        if (configFile != null) {
            configTextField.setText(configFile.getAbsolutePath());
            return;
        }
        // no config found
        StatusDisplayer.getDefault().setStatusText(Bundle.CustomizerJsTestDriver_config_none());
    }//GEN-LAST:event_configSearchButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton configBrowseButton;
    private JLabel configLabel;
    private JButton configSearchButton;
    private JTextField configTextField;
    // End of variables declaration//GEN-END:variables

    //~ Inner classes

    private final class DefaultDocumentListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            processChange();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            processChange();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            processChange();
        }

        private void processChange() {
            validateData();
        }

    }

}
