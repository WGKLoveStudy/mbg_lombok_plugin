package org.mybatis.generator.plugins.lombok;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;
import java.util.Properties;


public class LombokPlugin extends PluginAdapter {
    public  String CUSTOM_LOMBOK_ANNOTATIONS="customLombok";

    private String customLombok;

    public LombokPlugin() {
    }

    @Override
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        customLombok = properties.getProperty(CUSTOM_LOMBOK_ANNOTATIONS);
    }


    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        addLombokToTopLevelClass(topLevelClass,introspectedTable);
        return true;
    }



    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        addLombokToTopLevelClass(topLevelClass,introspectedTable);
        return true;
    }
    @Override
    public boolean modelRecordWithBLOBsClassGenerated(
            TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        addLombokToTopLevelClass(topLevelClass,introspectedTable);
        return true;
    }

    /**
     * with @Data or @Getter doesnt need getter method
     */
    @Override
    public boolean modelGetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              Plugin.ModelClassType modelClassType) {
        return isCustom() && !(customLombok.contains("@Data") || customLombok.contains("@Getter"));
    }

    /**
     * with @Data or @Setter doesnt need getter method
     */
    @Override
    public boolean modelSetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              Plugin.ModelClassType modelClassType) {
        return isCustom() && !(customLombok.contains("@Data") || customLombok.contains("@Setter"));
    }


    public boolean isCustom(){
        return !(customLombok==null || customLombok.length()==0);
    }

    public void addLombokToTopLevelClass(TopLevelClass topLevelClass,IntrospectedTable introspectedTable){
        if (isCustom()) {
            for (String annotation : customLombok.split(",")) {
                if (annotation.startsWith("@")){
                    topLevelClass.getAnnotations().add(annotation);
                    topLevelClass.addImportedType("lombok."+annotation.substring(1));
                }
            }
        }else {
            topLevelClass.getAnnotations().add("@Data");
            topLevelClass.getAnnotations().add("@NoArgsConstructor");
            topLevelClass.getAnnotations().add("@AllArgsConstructor");
            topLevelClass.getAnnotations().add("@Builder");
            topLevelClass.addImportedType("lombok.Data");
            topLevelClass.addImportedType("lombok.NoArgsConstructor");
            topLevelClass.addImportedType("lombok.AllArgsConstructor");
            topLevelClass.addImportedType("lombok.Builder");
        }
    }
}
