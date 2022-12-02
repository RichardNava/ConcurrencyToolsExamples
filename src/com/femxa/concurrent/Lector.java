/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.femxa.concurrent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author richa
 */
public class Lector implements Runnable {
    CyclicBarrier barrier;
    List<Integer> lista;
    String url;

    public Lector(CyclicBarrier barrier, List<Integer> lista, String url) {
        this.barrier = barrier;
        this.lista = lista;
        this.url = url;
    }
          
    @Override
    public void run() {
        try {
            Files.lines(Path.of(url))
                    .forEach(n -> lista.add(Integer.parseInt(n)));
            barrier.await();
        } catch (IOException | InterruptedException | BrokenBarrierException ex) {
            Logger.getLogger(Lector.class.getName()).log(Level.SEVERE, "No se ha encontrado el archivo", ex);
        } 
    }
    
}
