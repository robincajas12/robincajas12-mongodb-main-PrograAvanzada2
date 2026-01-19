package com.programacion.avanzada;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Tablero {
    @Getter
    private final int tamTablero;
    @Getter
    private final int blockSizeX;
    @Getter
    private final int blockSizeY;


    @Getter
    private final ImageInfo image;

    public Tablero(int tamTablero, ImageInfo image) {
        this.tamTablero = tamTablero;

        blockSizeY = image.width() / tamTablero;
        blockSizeX = image.height() / tamTablero;
        this.image = image;
    }

    void paint() {
        var cores = Runtime.getRuntime().availableProcessors();

        ExecutorService executor = Executors.newFixedThreadPool(cores);
        List<Future> tasks = new ArrayList<>();


        for (int row = 0; row < tamTablero; row++) {
            for (int col = 0; col < tamTablero; col++) {
                TaskTablero task = new TaskTablero(this, row, col);

                tasks.add(executor.submit(task));

            }
        }

        tasks.forEach(t -> {
            try {
                t.get();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        //contorno de la imagen
        int width = image.width();
        int height = image.height();

        int size = width * height;

        for (int i = 0; i < width; i++) {
            int index = i * ImageInfo.CHANNELS;

            image.pixelData()[index] = (byte) 0;
            image.pixelData()[index + 1] = (byte) 0;
            image.pixelData()[index + 2] = (byte) 0;
            image.pixelData()[index + 3] = (byte) 255;

            index = (size - i) * ImageInfo.CHANNELS - 4;
            image.pixelData()[index] = (byte) 0;
            image.pixelData()[index + 1] = (byte) 0;
            image.pixelData()[index + 2] = (byte) 0;
            image.pixelData()[index + 3] = (byte) 255;
        }

        for (int i = 0; i < height; i++) {
            int index = (i * width) * ImageInfo.CHANNELS;

            image.pixelData()[index] = (byte) 0;
            image.pixelData()[index + 1] = (byte) 0;
            image.pixelData()[index + 2] = (byte) 0;
            image.pixelData()[index + 3] = (byte) 255;

            index = ((i + 1) * width) * ImageInfo.CHANNELS - 4;
            image.pixelData()[index] = (byte) 0;
            image.pixelData()[index + 1] = (byte) 0;
            image.pixelData()[index + 2] = (byte) 0;
            image.pixelData()[index + 3] = (byte) 255;
        }

        System.out.println("final de todo");
        executor.close();
    }
}