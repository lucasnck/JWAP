package hibernate;



import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

public class CreateTables {

    public static void main(String args[]) {
        AnnotationConfiguration cfg = new AnnotationConfiguration();
        cfg.configure("/hibernate/hibernate.cfg.xml");
        SchemaExport se = new SchemaExport(cfg);
        se.create(true, true);
//        se.execute(true, true, false, true);
    }
    
}
