package tetris2018;

public class Figure {

    int[][] brick_1 = new int[1][2];
    int[][] brick_2 = new int[1][2];
    int[][] brick_3 = new int[1][2];
    int[][] brick_4 = new int[1][2];
    int ORIGIN_POSITION_X = 5,
            ORIGIN_POSITION_Y = 0;

    public Figure(int rand) {
        switch (rand) {
            case 0: {
                brick_1[0][0] = 0 + ORIGIN_POSITION_X;
                brick_1[0][1] = 3;

                brick_2[0][0] = 0 + ORIGIN_POSITION_X;
                brick_2[0][1] = 4;

                brick_3[0][0] = 1 + ORIGIN_POSITION_X;
                brick_3[0][1] = 3;

                brick_4[0][0] = 1 + ORIGIN_POSITION_X;
                brick_4[0][1] = 4;
                break;
            }

            case 1: {
                brick_1[0][0] = 1 + ORIGIN_POSITION_X;
                brick_1[0][1] = 0;

                brick_2[0][0] = 1 + ORIGIN_POSITION_X;
                brick_2[0][1] = 1;

                brick_3[0][0] = 1 + ORIGIN_POSITION_X;
                brick_3[0][1] = 2;

                brick_4[0][0] = 1 + ORIGIN_POSITION_X;
                brick_4[0][1] = 3;
                break;
            }
            case 2: {
                brick_4[0][0] = 0 + ORIGIN_POSITION_X;
                brick_4[0][1] = 3;

                brick_3[0][0] = 0 + ORIGIN_POSITION_X;
                brick_3[0][1] = 2;

                brick_2[0][0] = 0 + ORIGIN_POSITION_X;
                brick_2[0][1] = 1;

                brick_1[0][0] = 1 + ORIGIN_POSITION_X;
                brick_1[0][1] = 1;
                break;
            }
            case 3: {
                brick_1[0][0] = 0 + ORIGIN_POSITION_X;
                brick_1[0][1] = 3;

                brick_2[0][0] = 0 + ORIGIN_POSITION_X;
                brick_2[0][1] = 2;

                brick_3[0][0] = 1 + ORIGIN_POSITION_X;
                brick_3[0][1] = 2;

                brick_4[0][0] = 1 + ORIGIN_POSITION_X;
                brick_4[0][1] = 1;
                break;
            }
            case 4: {
                brick_1[0][0] = 1 + ORIGIN_POSITION_X;
                brick_1[0][1] = 3;

                brick_2[0][0] = 0 + ORIGIN_POSITION_X;
                brick_2[0][1] = 2;

                brick_3[0][0] = 1 + ORIGIN_POSITION_X;
                brick_3[0][1] = 2;

                brick_4[0][0] = 2 + ORIGIN_POSITION_X;
                brick_4[0][1] = 2;
                break;
            }
            // new Figure 
            case 5: {
                brick_4[0][0] = 1 + ORIGIN_POSITION_X;
                brick_4[0][1] = 3;

                brick_3[0][0] = 1 + ORIGIN_POSITION_X;
                brick_3[0][1] = 2;

                brick_2[0][0] = 1 + ORIGIN_POSITION_X;
                brick_2[0][1] = 1;

                brick_1[0][0] = 0 + ORIGIN_POSITION_X;
                brick_1[0][1] = 1;
                break;

            }
            // new Figure 
            case 6: {
                brick_1[0][0] = 1 + ORIGIN_POSITION_X;
                brick_1[0][1] = 3;

                brick_2[0][0] = 1 + ORIGIN_POSITION_X;
                brick_2[0][1] = 2;

                brick_3[0][0] = 0 + ORIGIN_POSITION_X;
                brick_3[0][1] = 2;

                brick_4[0][0] = 0 + ORIGIN_POSITION_X;
                brick_4[0][1] = 1;
                break;

            }

        }

    }
}
