package gui.vistas;

import conexionBD.ConexionVuelos;
import fechas.Fechas;
import quick.dbtable.DBTable;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class VistaEmpleadoTablas extends Vista {
    private JTabbedPane tabbedPane;
    private JPanel tablaIda;
    private JPanel tablaVuelta;
    private JPanel tablaVueloSeleccionado;
    private ConexionVuelos conn;


/*    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VistaEmpleadoTablas window = new VistaEmpleadoTablas();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }*/

    public VistaEmpleadoTablas(ConexionVuelos conn, String origen, String destino, String ida, String vuelta){
        this.conn = conn;
        initialize();

        tablaIda = new JPanel();
        tablaIda.setLayout(new BorderLayout());
        tablaIda.setBorder(BorderFactory.createEmptyBorder(80, 0, 0,0));
        tablaIda.add(createTable(origen, destino, ida), BorderLayout.CENTER);

        tablaVuelta = new JPanel();
        tablaVuelta.setLayout(new BorderLayout());
        tablaVuelta.setBorder(BorderFactory.createEmptyBorder(80, 0, 0,0));
        tablaVuelta.add(createTable(destino, origen, vuelta), BorderLayout.CENTER);

        tabbedPane.addTab("Ida", tablaIda);
        tabbedPane.addTab("Vuelta", tablaVuelta);

        frame.setVisible(true);

    }

    public VistaEmpleadoTablas(ConexionVuelos conn, String origen, String destino, String ida){
        this.conn = conn;

        initialize();

        tablaIda = new JPanel();
        tablaIda.setLayout(new BorderLayout());
        tablaIda.setBorder(BorderFactory.createEmptyBorder(80, 0, 0,0));
        tablaIda.add(createTable(origen, destino, ida), BorderLayout.CENTER);

        tabbedPane.addTab("Ida", tablaIda);

        frame.setVisible(true);

    }

    private JTable createTable(String origen, String destino, String fecha){
        try {
            conn.setSelectSql("SELECT vuelo, nombre_aeropuerto_salida, hora_sale, nombre_aeropuerto_llegada, hora_llega, modelo_avion, duracion_vuelo FROM vuelos_disponibles " +
                    "WHERE ciudad_aeropuerto_salida='" + origen + "' AND " +
                    "ciudad_aeropuerto_llegada='" + destino + "' AND " +
                    "fecha='" + Fechas.convertirStringADateSQL(fecha) + "';");
            conn.refresh();

            conn.createColumnModelFromQuery();
            for (int i = 0; i < conn.getColumnCount(); i++) {
                if(conn.getColumn(i).getType() != Types.CHAR) {
                    conn.getColumn(i).setType(Types.CHAR);
                }

            }

//            DefaultTableModel model = new DefaultTableModel();
//            for(int i = 0; i < conn.getTable().getModel().getColumnCount(); i++){
//                model.addColumn(conn.getColumn(i));
//            }
//
//            for(int j = 0; j < conn.getRowCount(); j++){
//                String[] fila = new String[conn.getColumnCount()];
//                for(int i = 0; i < conn.getColumnCount(); i++){
//                    fila[i] = ""+conn.getValueAt(j, i);
//                }
//
//                model.addRow(fila);
//            }


            return new JTable(conn.getDataVector(), conn.getSortColumns());
//            return new JTable(conn.getTable().getModel(), conn.getTable().getColumnModel());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void initialize() {
        frame.setBounds(100, 100, 500, 500);
        frame.setLayout(new BorderLayout());
        tabbedPane = new JTabbedPane();

        frame.add(tabbedPane, BorderLayout.CENTER);
    }
}
