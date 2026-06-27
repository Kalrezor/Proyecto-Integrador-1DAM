package com.dam.finanzas.main;

import java.awt.EventQueue;
import com.dam.finanzas.control.AppControlador;
import com.dam.finanzas.view.LoginView;
import com.dam.finanzas.view.RegisterView;

public class Inicio {

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                LoginView vlog = new LoginView();
                RegisterView vreg = new RegisterView();
                AppControlador controlador = new AppControlador(vlog, vreg);

                vlog.setController(controlador);
                vreg.setController(controlador);

                vlog.setVisible(true);
            }
        });
    }
}
