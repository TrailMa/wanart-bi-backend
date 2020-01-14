package wanart.bi.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

@Component
@DependsOn({"dldlJdbcTemplate", "mxzmJdbcTemplate"})
public class DataSourceRouterComponent {
    //@Autowired
    //@Qualifier("dldlJdbcTemplate")
    @Resource
    private JdbcTemplate dldlJdbcTemplate;

    //@Autowired
    //@Qualifier("mxzmJdbcTemplate")
    @Resource
    private JdbcTemplate mxzmJdbcTemplate;

    //@Autowired
    //@Qualifier("manageJdbcTemplate")
    @Resource
    private JdbcTemplate manageJdbcTemplate;

    public JdbcTemplate getJdbcTemplateByProjectName(String projectName){
        switch (projectName){
            case "dldl":
                return dldlJdbcTemplate;
            case "mxzm":
                return mxzmJdbcTemplate;
            case "manage":
                return manageJdbcTemplate;
           default:
               return  null;
        }
    }
}
