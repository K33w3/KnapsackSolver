package com.bcs2024.knapsack.algorithm;

import com.bcs2024.knapsack.renderer.HelloApplication;
import com.bcs2024.knapsack.model.CargoSpace;
import com.bcs2024.knapsack.model.Cell;
import com.bcs2024.knapsack.model.Header;
import com.bcs2024.knapsack.model.ParcelInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DancingLinks {

    public Header root;
    public Header[] headers;
    public Stack<Integer> answer;
    public Stack<Integer> pentIDS;
    public static int length = (int) (CargoSpace.length * 2);
    public static int height = (int) (CargoSpace.height * 2);
    public static int width = (int) (CargoSpace.width * 2);
    public static boolean stop = false;
    public static int[][][] field;
    private CargoSpace cargoSpace;

    public DancingLinks(int columns) {
        cargoSpace = new CargoSpace();
        answer = new Stack<>();
        pentIDS = new Stack<>();

        root = new Header(-1);
        headers = new Header[columns];
        for (int j = 0; j < columns; j++) {
            headers[j] = new Header(j);
            root.InsertLeft(headers[j]);
        }

        field = new int[width][height][length];
    }

    public void AddRow(int row, int pentId, int[] ones, int[][][] piece) {
        int last = -1;
        Cell first = null;
        for (int x : ones) {
            Cell cell = new Cell(headers[x]);
            headers[x].InsertUp(cell);
            cell.row = row;
            cell.shape = piece;
            cell.pentID = pentId;

            headers[x].size++;

            if (x <= last) {
                throw new IllegalArgumentException("Column indexes must be in increasing order");
            }

            if (first == null) {
                first = cell;
            } else {
                first.InsertLeft(cell);
            }
        }
    }

    public int countA = 0;
    public int countB = 0;
    public int countC = 0;

    public void algorithmX(int step) {
        if (stop) return;
        List<ParcelInfo> parcelInfo = new ArrayList<>();
        if(answer.size() >= 10){
            DLSearch.pieceCount = 0;
            DLSearch.totalValue = 0;
            for(var ans : answer)
            {
                ParcelInfo r = DLSearch.parcelInfo.get(ans);
                parcelInfo.add(r);
            }

            clearField();

            for(var info : parcelInfo)
            {
                cargoSpace.placeParcel(info.shape, info.x0, info.y0, info.z0, field);
                DLSearch.pieceCount++;
                DLSearch.totalValue += info.pieceValue;
                switch (info.parcelID) {
                    case 1 -> countA++;
                    case 2 -> countB++;
                    case 3 -> countC++;
                }
            }
            System.out.println("Total value: " + DLSearch.totalValue);
            System.out.println(countA + " " + countB + " " + countC);
            if (countA != 0 || countB != 0 || countC != 0) {
                if (DLSearch.totalValue >= 228) {
                    stop = true;
                    HelloApplication ui = new HelloApplication();
                    ui.show();
                    return;
                }
            } else if (root.R == root) {
                stop = true;
                HelloApplication ui = new HelloApplication();
                ui.show();
                return;
            }
            countA = 0; countB = 0; countC = 0;
            field = new int[width][height][length];
        }

        Header head = (Header) root.R;
        int minSize = head.size;
        for (Cell xCell = head; xCell != root; xCell = xCell.R)
        {
            if (((Header) xCell).size < minSize) {
                minSize = ((Header) xCell).size;
                head = (Header) xCell;

                if (head.C.size == 0) {
                    return;
                }
            }
        }
        cover(head);
        for (Cell rCell = head.D; rCell != head; rCell = rCell.D) {
            answer.push(rCell.row);
            pentIDS.push(rCell.pentID);

            for (Cell jCell = rCell.R; jCell != rCell; jCell = jCell.R) {
                cover(jCell.C);
            }
            algorithmX(step + 1);
            answer.pop();
            pentIDS.pop();

            for (Cell jCell = rCell.L; jCell != rCell; jCell = jCell.L) {
                uncover(jCell.C);
            }

        }
        uncover(head);
    }

    private void clearField() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                for (int k = 0; k < field[0][0].length; k++) {
                    field[i][j][k] = -1;
                }
            }
        }
    }

    private void cover(Header head) {
        head.R.L = head.L;
        head.L.R = head.R;

        for (Cell iCell = head.D; iCell != head; iCell = iCell.D) {
            for (Cell jCell = iCell.R; iCell != jCell; jCell = jCell.R) {
                jCell.D.U = jCell.U;
                jCell.U.D = jCell.D;
                jCell.C.size--;
            }
        }
    }

    private void uncover(Header head) {
        for (Cell iCell = head.U; iCell != head; iCell = iCell.U)
            for (Cell jCell = iCell.L; jCell != iCell; jCell = jCell.L) {
                jCell.D.U = jCell;
                jCell.U.D = jCell;
                jCell.C.size++;
            }
        head.R.L = head;
        head.L.R = head;
    }

}

