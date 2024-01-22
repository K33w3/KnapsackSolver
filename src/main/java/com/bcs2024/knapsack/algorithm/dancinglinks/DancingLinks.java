package com.bcs2024.knapsack.algorithm.dancinglinks;

import com.bcs2024.knapsack.model.CargoSpace;
import com.bcs2024.knapsack.renderer.UI;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DancingLinks {

    public static int length = (int) (CargoSpace.length);
    public static int height = (int) (CargoSpace.height);
    public static int width = (int) (CargoSpace.width);
    public boolean stop = false;
    public int pieceCount = 0;
    public int totalValue = 0;
    public Header root;
    public Header[] headers;
    public Stack<Integer> answer;
    public Stack<Integer> pentIDS;
    public int duplicateSolutionsFound;
    public int countA = 0;
    public int countB = 0;
    public int countC = 0;
    private final CargoSpace cargoSpace = UI.cargoSpace;

    private final List<ParcelInfo> parcelInfoList = new ArrayList<>();

    public DancingLinks(final int columns) {
        answer = new Stack<>();
        pentIDS = new Stack<>();

        duplicateSolutionsFound = 0;

        root = new Header(-1);
        headers = new Header[columns];
        for (int j = 0; j < columns; j++) {
            headers[j] = new Header(j);
            root.InsertLeft(headers[j]);
        }
    }

    public void AddRow(final int row, final int pentId, final int[] ones, final int[][][] piece) {
        final int last = -1;
        Cell first = null;
        for (final int x : ones) {
            final Cell cell = new Cell(headers[x]);
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

    public void algorithmX(final int step) {
        if (stop) return;

        final List<ParcelInfo> parcelInfo = new ArrayList<>();

        if (answer.size() >= 10) {
            pieceCount = 0;
            totalValue = 0;

            for (final var ans : answer) {
                final ParcelInfo r = parcelInfoList.get(ans);
                parcelInfo.add(r);
            }

            for (int i = 0; i < cargoSpace.getOccupied().length; i++) {
                for (int j = 0; j < cargoSpace.getOccupied()[0].length; j++) {
                    for (int k = 0; k < cargoSpace.getOccupied()[0][0].length; k++) {
                        cargoSpace.setOccupiedCell(i, j, k, -1);
                    }
                }
            }

            for (final var info : parcelInfo) {
                cargoSpace.placeParcel(info.shape, info.x0, info.y0, info.z0);
                pieceCount++;
                totalValue += info.pieceValue;
                switch (info.parcelID) {
                    case 1 -> countA++;
                    case 2 -> countB++;
                    case 3 -> countC++;
                }
            }

            System.out.println("Total value: " + totalValue);
            System.out.println(countA + " " + countB + " " + countC);

            countA = 0;
            countB = 0;
            countC = 0;

            if (root.R == root) {
                stop = true;
                return;
            }

            cargoSpace.setOccupied(new int[width][height][length]);
        }

        Header head = (Header) root.R;
        int minSize = head.size;
        for (Cell xCell = head; xCell != root; xCell = xCell.R) {
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

    public void addParcelInfoToList(final ParcelInfo parcelInfo) {
        this.parcelInfoList.add(parcelInfo);
    }

    private void cover(final Header head) {
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

    private void uncover(final Header head) {
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
