package Objets;

/**
 * Created by Kelly on 22/03/2018.
 */

public class Id {
    private static int idu_max;
    private static int ide_max;
    private static int idt_max;
    private static int idl_max;
    private static int idligne_max;
    private static int idd_max;

    public Id() {
        idu_max = -1;
        ide_max = -1;
        idt_max = -1;
        idl_max = -1;
        idligne_max = -1;
        idd_max = -1;
    }

    // SETTER
    public static void set_idu_max(int idu_max) {
        Id.idu_max = idu_max;
    }

    public static void set_ide_max(int ide_max) {
        Id.ide_max = ide_max;
    }

    public static void set_idt_max(int idt_max) {
        Id.idt_max = idt_max;
    }

    public static void set_idl_max(int idl_max) {
        Id.idl_max = idl_max;
    }
    public static void set_idligne_max(int idligne_max) {
        Id.idligne_max = idligne_max;
    }
    public static void set_idd_max(int idd_max) {
        Id.idd_max = idd_max;
    }

    //GETTER

    public static int get_idu_max() {
        return idu_max;
    }

    public static int get_ide_max() {
        return ide_max;
    }

    public static int get_idt_max() {
        return idt_max;
    }

    public static int get_idl_max() {
        return idl_max;
    }

    public static int get_idligne_max() {
        return idligne_max;
    }
    public static int get_idd_max() {
        return idd_max;
    }

}


