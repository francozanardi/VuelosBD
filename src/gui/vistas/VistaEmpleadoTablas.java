package gui.vistas;

import conexionBD.ConexionVuelos;
import fechas.Fechas;
import quick.dbtable.DBTable;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.sql.Types;

public class VistaEmpleadoTablas extends Vista {
    private JTabbedPane tabbedPane;
    private DBTable tablaIda, tablaVuelta, tablaVueloSeleccionado;
    private String fechaIda, fechaVuelta;
    private JPanel panelBotones;
    private JButton seleccionar;
    private ConexionVuelos conn;



    public VistaEmpleadoTablas(ConexionVuelos conn, String origen, String destino, String ida, String vuelta){
        this.conn = conn;
        tablaVueloSeleccionado = null;

        fechaIda = ida;
        fechaVuelta = vuelta;

        tablaIda = createTableBusqueda(origen, destino, ida);
        tablaVuelta = createTableBusqueda(destino, origen, vuelta);

        initialize();
        tabbedPane.addTab("Ida", tablaIda);
        tabbedPane.addTab("Vuelta", tablaVuelta);

        frame.setVisible(true);

    }

    public VistaEmpleadoTablas(ConexionVuelos conn, String origen, String destino, String ida){
        this.conn = conn;
        tablaVueloSeleccionado = null;

        fechaIda = ida;

        tablaIda = createTableBusqueda(origen, destino, ida);

        initialize();
        tabbedPane.addTab("Ida", tablaIda);

        frame.setVisible(true);

    }

    @Override
    public void finalizarVista() {
        super.finalizarVista();
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private DBTable createTableBusqueda(String origen, String destino, String fecha){
        try {
            if(conn.isConnectionValid()){
                DBTable tb = new ConexionVuelos(conn);
                tb.setEditable(false);
                tb.setSelectSql("SELECT DISTINCT vuelo, nombre_aeropuerto_salida, hora_sale, nombre_aeropuerto_llegada, hora_llega, modelo_avion, duracion_vuelo FROM vuelos_disponibles " +
                        "WHERE ciudad_aeropuerto_salida='" + origen + "' AND " +
                        "ciudad_aeropuerto_llegada='" + destino + "' AND " +
                        "fecha='" + Fechas.convertirStringADateSQL(fecha) + "';");
                tb.refresh();

                tb.createColumnModelFromQuery();
                for (int i = 0; i < tb.getColumnCount(); i++) {
                    if(tb.getColumn(i).getType() != Types.CHAR) {
                        tb.getColumn(i).setType(Types.CHAR);
                    }

                }
                return tb;
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Se perdió la conexión con la base de datos.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame,
                    "Se produjo un error inesperado en la base de datos.\nVuelva a intentarlo, si el error persiste contacte con el administrador.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        return new DBTable();
    }

    private DBTable createTableSeleccion(String vuelo, String fecha){
        try {
            DBTable tb = new ConexionVuelos(conn);
            tb.setEditable(false);
            tb.setSelectSql("SELECT clase, asientos_disp, precio_pasaje FROM vuelos_disponibles WHERE vuelo='" + vuelo + "' AND fecha='" + Fechas.convertirStringADateSQL(fecha) + "';");

            tb.refresh();

            return tb;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void initialize() {
        frame.setBounds(100, 100, 500, 500);
        frame.setLayout(new BorderLayout(0, 10));
        frame.setTitle("Empleado | Vuelos disponibles");

        tabbedPane = new JTabbedPane();

        seleccionar = new JButton("Seleccionar");
        seleccionar.addActionListener(e -> {
            DBTable selec = null;
            String fecha = "";
            if(tabbedPane.getComponentAt(tabbedPane.getSelectedIndex()) == tablaIda){
                selec = tablaIda;
                fecha = fechaIda;
            } else if(tabbedPane.getComponentAt(tabbedPane.getSelectedIndex()) == tablaVuelta){
                selec = tablaVuelta;
                fecha = fechaVuelta;
            }

            int rowSelected;
            String vuelo = "";
            if(selec != null){
                if(selec.getSelectedRowCount() == 1){
                    rowSelected = selec.getSelectedRow();
                    for(int i = 0; i < selec.getColumnCount(); i++){
                        if(selec.getColumn(i).getColumnName().toLowerCase().equals("vuelo")){
                            vuelo = selec.getValueAt(rowSelected, i).toString();
                            break;
                        }
                    }

                    if(tablaVueloSeleccionado != null){
                        tabbedPane.removeTabAt(tabbedPane.indexOfComponent(tablaVueloSeleccionado));
                    }
                    tablaVueloSeleccionado = createTableSeleccion(vuelo, fecha);
                    tabbedPane.addTab("Vuelo seleccionado", tablaVueloSeleccionado);

                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Debe seleccionar una única fila.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        panelBotones = new JPanel();
        panelBotones.setLayout(new BorderLayout());
        panelBotones.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelBotones.add(seleccionar);

        tabbedPane.addChangeListener(e -> {
            seleccionar.setEnabled(tabbedPane.indexOfComponent(tablaVueloSeleccionado) != tabbedPane.getSelectedIndex());
        });

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.add(panelBotones, BorderLayout.SOUTH);

    }
}
