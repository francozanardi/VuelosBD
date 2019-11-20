package gui.vistas;

import conexionBD.ConexionVuelos;
import fechas.Fechas;
import gui.vistas.excepciones.PasajeroNotFoundException;
import quick.dbtable.DBTable;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class VistaEmpleadoTablas extends Vista {
    private JTabbedPane tabbedPane;
    private DBTable tablaIda, tablaVuelta, tablaSeleccionIda, tablaSeleccionVuelta;
    private String fechaIda, fechaVuelta, tipoDoc_pasajero, nroDoc_pasajero, nroLeg_empleado;
    private JPanel panelBotones, panelTablasResultantes, panelSeleccionIda, panelSeleccionVuelta;
    private JButton btnSeleccionar, btnReservar;
    private ConexionVuelos conn;

    public VistaEmpleadoTablas(ConexionVuelos conn, String origen, String destino, String ida, String vuelta, String tipoDoc_pasajero, String nroDoc_pasajero, String nroLeg_empleado) throws PasajeroNotFoundException {
        this.conn = conn;
        this.tablaSeleccionIda = null;
        this.tablaSeleccionVuelta = null;

        this.nroLeg_empleado = nroLeg_empleado;
        this.nroDoc_pasajero = nroDoc_pasajero;
        this.tipoDoc_pasajero = tipoDoc_pasajero;

        fechaIda = ida;
        fechaVuelta = vuelta;

        checkPasajero();
        tablaIda = createTableBusqueda(origen, destino, ida);
        tablaVuelta = createTableBusqueda(destino, origen, vuelta);

        initialize();
        tabbedPane.addTab("Ida", tablaIda);
        tabbedPane.addTab("Vuelta", tablaVuelta);

        frame.setVisible(true);
    }

    public VistaEmpleadoTablas(ConexionVuelos conn, String origen, String destino, String ida, String tipoDoc_pasajero, String nroDoc_pasajero, String nroLeg_empleado) throws PasajeroNotFoundException {
        this.conn = conn;
        this.tablaSeleccionIda = null;
        this.tablaSeleccionVuelta = null;

        this.nroLeg_empleado = nroLeg_empleado;
        this.nroDoc_pasajero = nroDoc_pasajero;
        this.tipoDoc_pasajero = tipoDoc_pasajero;

        fechaIda = ida;

        checkPasajero();
        tablaIda = createTableBusqueda(origen, destino, ida);

        initialize();
        tabbedPane.addTab("Ida", tablaIda);

        frame.setVisible(true);

    }

    private void checkPasajero() throws PasajeroNotFoundException {
        try {
            if(conn.isConnectionValid()){
                Statement st = conn.getConnection().createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM pasajeros WHERE doc_tipo = '" + tipoDoc_pasajero + "' AND doc_nro = '" + nroDoc_pasajero + "';");

                if(!rs.first()){
                    throw new PasajeroNotFoundException("No existe el pasajero recibido.");
                }

                rs.close();
                st.close();
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

                tb.getTable().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

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
            tb.setSelectSql("SELECT vuelos_disponibles.vuelo as vuelo, vuelos_disponibles.fecha as fecha, clase, asientos_disp, precio_pasaje FROM vuelos_disponibles WHERE vuelo='" + vuelo + "' AND fecha='" + Fechas.convertirStringADateSQL(fecha) + "' AND asientos_disp > 0;");
            tb.refresh();

            tb.setMinimumSize(null);
            tb.setPreferredSize(null);
            tb.setMaximumSize(null);

            tb.getTable().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

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

        createButtons();

        panelTablasResultantes = new JPanel();
        panelTablasResultantes.setLayout(new BorderLayout(0, 15));
        panelTablasResultantes.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.add(panelBotones, BorderLayout.SOUTH);

        panelTablasResultantes.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeTables();
            }
        });

        panelSeleccionIda = new JPanel();
        panelSeleccionIda.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (), "Vuelos de ida", TitledBorder.CENTER, TitledBorder.TOP));

        panelSeleccionVuelta = new JPanel();
        panelSeleccionVuelta.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (), "Vuelos de vuelta", TitledBorder.CENTER, TitledBorder.TOP));

        panelTablasResultantes.add(panelSeleccionIda, BorderLayout.NORTH);

    }

    private void createButtons(){

        panelBotones = new JPanel();
        panelBotones.setLayout(new BorderLayout());
        panelBotones.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        createButtonSeleccionar();
        panelBotones.add(btnSeleccionar);

        createButtonReservar();

        tabbedPane.addChangeListener(e -> {
            boolean esSeleccion =   tabbedPane.indexOfComponent(tablaIda) == tabbedPane.getSelectedIndex() ||
                                    tabbedPane.indexOfComponent(tablaVuelta) == tabbedPane.getSelectedIndex();

            if(esSeleccion){
                panelBotones.remove(btnReservar);
                panelBotones.add(btnSeleccionar);
            } else {
                panelBotones.remove(btnSeleccionar);
                panelBotones.add(btnReservar);
            }

            SwingUtilities.updateComponentTreeUI(panelBotones);
        });




    }

    private void createButtonSeleccionar() {
        btnSeleccionar = new JButton("Seleccionar");

        btnSeleccionar.addActionListener(e -> {
            DBTable tablaSeleccionada = null;
            String fecha = "";
            if(tabbedPane.getComponentAt(tabbedPane.getSelectedIndex()) == tablaIda){
                tablaSeleccionada = tablaIda;
                fecha = fechaIda;
            } else if(tabbedPane.getComponentAt(tabbedPane.getSelectedIndex()) == tablaVuelta){
                tablaSeleccionada = tablaVuelta;
                fecha = fechaVuelta;
            }


            int rowSelected;
            String vuelo = "";
            if(tablaSeleccionada != null){
                if(tablaSeleccionada.getSelectedRowCount() == 1){
                    //buscamos el vuelo en al fila seleccionada.
                    rowSelected = tablaSeleccionada.getSelectedRow();
                    for(int i = 0; i < tablaSeleccionada.getColumnCount(); i++){
                        if(tablaSeleccionada.getColumn(i).getColumnName().toLowerCase().equals("vuelo")){
                            vuelo = tablaSeleccionada.getValueAt(rowSelected, i).toString();
                            break;
                        }
                    }

                    if(tablaSeleccionada == tablaIda){
                        if(tablaSeleccionIda != null){
                            panelSeleccionIda.remove(tablaSeleccionIda);
                        }

                        tablaSeleccionIda = createTableSeleccion(vuelo, fecha);
                        panelSeleccionIda.add(tablaSeleccionIda, BorderLayout.CENTER);

                    } else {
                        if(tablaSeleccionVuelta != null){
                            panelSeleccionVuelta.remove(tablaSeleccionVuelta);
                        }

                        tablaSeleccionVuelta = createTableSeleccion(vuelo, fecha);
                        panelSeleccionVuelta.add(tablaSeleccionVuelta, BorderLayout.CENTER);

//                        containerContainsComponent(panelTablasResultantes, panelSeleccionVuelta)
                        if(!panelTablasResultantes.isAncestorOf(panelSeleccionVuelta)){
                            panelTablasResultantes.add(panelSeleccionVuelta, BorderLayout.SOUTH);
                        }
                    }

                    if(tabbedPane.indexOfComponent(panelTablasResultantes) == -1){
                        tabbedPane.add("Elección de vuelo", panelTablasResultantes);
                    }

                    tabbedPane.setSelectedIndex(tabbedPane.indexOfComponent(panelTablasResultantes));

                    resizeTables();

                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Debe seleccionar una única fila.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }

        });

    }

    private void createButtonReservar() {
        btnReservar = new JButton("Reservar");

        btnReservar.addActionListener(e -> {
            boolean esIdaVuelta = tablaVuelta != null;
            if(tabbedPane.getComponentAt(tabbedPane.getSelectedIndex()) == panelTablasResultantes){
                if(tablaSeleccionIda != null && !(esIdaVuelta && tablaSeleccionVuelta == null)){
                    if(tablaSeleccionIda.getSelectedRowCount() == 1 && !(esIdaVuelta && tablaSeleccionVuelta.getSelectedRowCount() != 1)){
                        try {
                            if(conn.isConnectionValid()){
                                int rowSelected = tablaSeleccionIda.getSelectedRow();
                                String vueloIda = getElementDBTable(tablaSeleccionIda, rowSelected, "vuelo");
                                String claseIda = getElementDBTable(tablaSeleccionIda, rowSelected, "clase");

                                Statement st = conn.getConnection().createStatement();
                                ResultSet rs;

                                if(esIdaVuelta){
                                    rowSelected = tablaSeleccionVuelta.getSelectedRow();
                                    String vueloVuelta = getElementDBTable(tablaSeleccionVuelta, rowSelected, "vuelo");
                                    String claseVuelta = getElementDBTable(tablaSeleccionVuelta, rowSelected, "clase");

                                    rs = st.executeQuery(String.format("call reservarVueloIdaVuelta('%s', %s, %s, '%s', '%s', '%s', '%s', '%s', '%s');", tipoDoc_pasajero, nroDoc_pasajero, nroLeg_empleado, vueloIda, Fechas.convertirStringADateSQL(fechaIda).toString(), claseIda, vueloVuelta, Fechas.convertirStringADateSQL(fechaVuelta).toString(), claseVuelta));
                                } else {
                                    rs = st.executeQuery(String.format("call reservarVueloIda('%s', %s, %s, '%s', '%s', '%s');", tipoDoc_pasajero, nroDoc_pasajero, nroLeg_empleado, vueloIda, Fechas.convertirStringADateSQL(fechaIda).toString(), claseIda));
                                }

                                if(rs.next()){
                                    if(rs.getString(2).toLowerCase().equals("error")){
                                        JOptionPane.showMessageDialog(frame,
                                                rs.getString(1),
                                                "Error",
                                                JOptionPane.ERROR_MESSAGE);
                                    } else {
                                        btnReservar.setEnabled(false);
                                        JOptionPane.showMessageDialog(frame,
                                                rs.getString(1),
                                                "Operación exitosa",
                                                JOptionPane.INFORMATION_MESSAGE);
                                    }
                                }

                            } else {
                                JOptionPane.showMessageDialog(frame,
                                        "Se ha perdido la conexión. Intente conectarse nuevamente.",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (SQLException e1) {
//                            e1.printStackTrace();
                            JOptionPane.showMessageDialog(frame,
                                    "Se ha producido un error inesperado.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(frame,
                                "Debe seleccionar una única fila.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Debe seleccionar tanto el vuelo para la ida como para la vuelta.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }

        });
    }



    private void resizeTables(){
        int divisor = tablaSeleccionIda != null && tablaSeleccionVuelta != null ? 2 : 1;

        if (tablaSeleccionIda != null) {
            tablaSeleccionIda.setPreferredSize(new Dimension(panelSeleccionIda.getWidth()-25, panelTablasResultantes.getHeight() / divisor - 50));
        }

        if (tablaSeleccionVuelta != null) {
            tablaSeleccionVuelta.setPreferredSize(new Dimension(panelSeleccionIda.getWidth()-25, panelTablasResultantes.getHeight() / divisor - 50));
        }

        SwingUtilities.updateComponentTreeUI(panelTablasResultantes);
    }

    public String getElementDBTable(DBTable tb, int fila, String columnName){
        for(int i = 0; i < tb.getColumnCount(); i++){
            if(tb.getColumn(i).getColumnName().toLowerCase().equals(columnName)){
                return tb.getValueAt(fila, i).toString();
            }
        }

        return "";
    }
}
