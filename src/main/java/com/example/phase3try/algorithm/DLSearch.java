package com.example.phase3try.algorithm;

import com.example.phase3try.model.CargoSpace;
import com.example.phase3try.model.Parcel;
import com.example.phase3try.model.ParcelInfo;
import com.example.phase3try.model.ShapesAndRotations;

import java.util.ArrayList;
import java.util.List;

public class DLSearch {
    private ShapesAndRotations shapesAndRotations = new ShapesAndRotations();
    public static int length = (int) (CargoSpace.length * 2);
    public static int height = (int) (CargoSpace.height * 2);
    public static int width = (int) (CargoSpace.width * 2);
    public static int totalValue = 0;
//    private String[] sequence = {"C", "B", "A"};
    private String[] sequence = {"T", "P", "L"};
    DancingLinks dance = new DancingLinks(width * height * length);
    public static List<ParcelInfo> parcelInfo = new ArrayList<ParcelInfo>();
    public static int pieceCount = 0;
    private int currentPieceValue;

    public void createPositions(){
        int nr = 0;
        for(String type : sequence){
            Parcel parcel = new Parcel(type);
            currentPieceValue = parcel.getValue();
            for(int rotation = 0; rotation < shapesAndRotations.rotationNum(type); rotation++){
                int[][][] shape = shapesAndRotations.getShape(type, rotation);
                int shapeWidth = shape[0][0].length;
                int shapeHeight = shape[0].length;
                int shapeLength = shape.length;
                for(int z=0; z < length; z++){
                    for(int y=0; y < height; y++){
                        for(int x=0; x < width; x++){
                            if(!canPlace(x, y, z, shape)){
                                continue;
                            }
                            List<Integer> xs = getOccupiedCellsX(shape, x);
                            List<Integer> ys = getOccupiedCellsY(shape, y);
                            List<Integer> zs = getOccupiedCellsZ(shape, z);

                            int[] positions = null;

                            if (type == "L" || type == "P" || type == "T") {
                                positions = new int[5];
                            } else {
                                positions = new int[shapeLength * shapeHeight * shapeWidth];
                            }


                            for(int i = 0; i < positions.length; i++){
                                positions[i] = length * height * xs.get(i) + length * ys.get(i) + zs.get(i);
                            }

                            parcelInfo.add(new ParcelInfo(nr, x, y, z, parcel.getId(), shape, currentPieceValue));
                            dance.AddRow(nr, parcel.getId(), positions, shape);
                            nr++;
                        }
                    }
                }
            }
        }
        dance.algorithmX(0);
    }

    public boolean canPlace(int startX, int startY, int startZ, int[][][] shape) {

        int shapeWidth = shape[0][0].length;
        int shapeHeight = shape[0].length;
        int shapeDepth = shape.length;

        if(startX + shapeWidth > width){
            return false;
        }

        if(startY + shapeHeight > height){
            return false;
        }

        return startZ + shapeDepth <= length;
    }

    public List<Integer> getOccupiedCellsX(int[][][] pieceToPlace, int x0) {
        List<Integer> xs = new ArrayList<Integer>();

        for(int z=0; z<pieceToPlace.length; z++) {
            for(int y=0; y<pieceToPlace[0].length; y++) {
                for(int x=0; x<pieceToPlace[0][0].length; x++) {
                    if(pieceToPlace[z][y][x] != 0) {
                        xs.add(x + x0);
                    }
                }
            }
        }
        return xs;

    }

    public List<Integer> getOccupiedCellsY(int[][][] pieceToPlace, int y0) {
        List<Integer> ys = new ArrayList<Integer>();

        for(int z=0; z<pieceToPlace.length; z++) {
            for(int y=0; y<pieceToPlace[0].length; y++) {
                for(int x=0; x<pieceToPlace[0][0].length; x++) {
                    if(pieceToPlace[z][y][x] != 0) {
                        ys.add(y + y0);
                    }
                }
            }
        }
        return ys;
    }

    public List<Integer> getOccupiedCellsZ(int[][][] pieceToPlace, int z0) {
        List<Integer> zs = new ArrayList<Integer>();

        for (int i = 0; i < pieceToPlace.length; i++) {
            for (int j = 0; j < pieceToPlace[0].length; j++) {
                for (int k = 0; k < pieceToPlace[0][0].length; k++) {
                    if (pieceToPlace[i][j][k] != 0) {
                        zs.add(i + z0);
                    }
                }
            }
        }
        return zs;
    }


    public static void main(String[] args) {
        DLSearch dlx = new DLSearch();
        dlx.createPositions();
    }
}
