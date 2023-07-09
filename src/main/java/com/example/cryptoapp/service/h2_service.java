package com.example.cryptoapp.service;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.DriverManager;

public class h2_service {
    final private static String url = "http://localhost:8080/h2-console";

    public static void start() {
        try {
            Server.createTcpServer("-tcpAllowOthers").start();
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.
                    getConnection("jdbc:h2:mem:cryptodb", "sa", "");
            System.out.println("Connection Established: "
                    + conn.getMetaData().getDatabaseProductName() + "/" + conn.getCatalog());

            System.out.println("H2 ACCESS URL: " + url);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
