//Please don't change class name 'Main'
import sun.security.util.Length;

import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        try(Scanner s = new Scanner(System.in))
        {
            int x1 = s.nextInt();
            int y1 = s.nextInt();
            int x2 = s.nextInt();
            int y2 = s.nextInt();
            int x3 = s.nextInt();
            int y3 = s.nextInt();
            int x4 = s.nextInt();
            int y4 = s.nextInt();
            int x5 = s.nextInt();
            int y5 = s.nextInt();
            int x6 = s.nextInt();
            int y6 = s.nextInt();


            //여기부터 작성해 주세요
            //구하는 영역은 1번 영역에서 2과 겹친 부분빼고 3과 겹친 부분 빼고 123모두 겹친 부분 더하는 것
            int[] area1 = {x1,x2,y1,y2};
            int[] area2 = {x3,x4,y3,y4};
            int[] area3 = {x5,x6,y5,y6};

            int[] crossedArea1And2 = getCrossdArea(area1, area2);
            int[] crossedArea1And3 = getCrossdArea(area1, area3);
            int[] crossedArea1And2And3 = getCrossdArea(crossedArea1And2, crossedArea1And3);

            int sizeOfarea1 = (area1[1]-area1[0]) * (area1[3] - area1[2]);
            int sizeOfcrossedArea1And2 = (crossedArea1And2[1]-crossedArea1And2[0]) * (crossedArea1And2[3] - crossedArea1And2[2]);
            int sizeOfcrossedArea1And3 = (crossedArea1And3[1]-crossedArea1And3[0]) * (crossedArea1And3[3] - crossedArea1And3[2]);
            System.out.println(crossedArea1And2[0] + " " + crossedArea1And2[1] + " " + crossedArea1And2[2] + " " + crossedArea1And2[3]);
            System.out.println(crossedArea1And3[0] + " " + crossedArea1And3[1] + " " + crossedArea1And3[2] + " " + crossedArea1And3[3]);
            int sizeOfcrossedArea1And2And3 = (crossedArea1And2And3[1]-crossedArea1And2And3[0]) * (crossedArea1And2And3[3] - crossedArea1And2And3[2]);

            int resultSize = sizeOfarea1 - sizeOfcrossedArea1And2 - sizeOfcrossedArea1And3 + sizeOfcrossedArea1And2And3;
            System.out.println(sizeOfarea1);
            System.out.println(sizeOfcrossedArea1And2);
            System.out.println(sizeOfcrossedArea1And3);
            System.out.println(sizeOfcrossedArea1And2And3);
            System.out.print(resultSize);
        }
    }

    public static int[] getCrossdArea(int[] area1, int[] area2){
        int xLength = 0;
        int yLength = 0;
        int aLeftx = area1[0];
        int aRightx = area1[1];
        int aLowy = area1[2];
        int aHighy = area1[3];

        int bLeftx = area2[0];
        int bRightx = area2[1];
        int bLowy = area2[2];
        int bHighy = area2[3];

        int[] crossedArea = {0,0,0,0};
        //두번째 직사각형 왼쪽 x좌표가 첫번쨰 직사각형의 왼쪽보다 더 왼쪽에 있는경우
        if (bLeftx <= aLeftx){
            //두번째 직사각형이 첫번째보다 아예 왼쪽에있고 안겹치는 상황
            if(bRightx <= aLeftx){
                crossedArea[0] = 0;
                crossedArea[1] = 0;
            //두번째 직사각형 오른쪽 x좌표가 첫번쨰 직사각형의 사이에 있는 경우
            }else if(aLeftx <= bRightx && aRightx >= bRightx){
                crossedArea[0] = aLeftx;
                crossedArea[1] = bRightx;
            //두번째 직사각형의 너비가 첫번쨰 사각형을 포함한경우
            }else if(aRightx <= bRightx){
                crossedArea[0] = aLeftx;
                crossedArea[1] = aRightx;
            }
        }
        //두번쨰 영역의 왼쪽 x좌표가 첫번째의 사이에 있는 경우
        else if (aLeftx <= bLeftx && bLeftx <= aRightx){
            //두번째 영역의 너비가 첫번쨰 영역의 너비에 포함될 때
            if(bRightx <= aRightx){
                crossedArea[0] = bLeftx;
                crossedArea[1] = bRightx;
            }
            //두번째 영역이 왼쪽으로 튀어나와 겹친 경우
            else if(aRightx <= bRightx){
                crossedArea[0] = bLeftx;
                crossedArea[1] = aRightx;
            }
        }
        //두번쩨 영역의 왼쪽 좌표가 첫번째 오른쪽 좌표보다 오른쪽에 있는경우
        else if(aRightx < bLeftx){
            crossedArea[0] = 0;
            crossedArea[1] = 0;
        }else{
            crossedArea[0] = 0;
            crossedArea[1] = 0;
        }

        //Left => Low , Right => High
        //x는 오른쪽에 있는게 값이 크고, y는 위쪽에 있는게 값이 큼
        if (bLowy <= aLowy){
            if(bHighy <= aLowy){
                crossedArea[2] = 0;
                crossedArea[3] = 0;
            }else if(aLowy <= bHighy && aHighy >= bLowy){
                crossedArea[2] = aLowy;
                crossedArea[3] = bHighy;
            }else if(aHighy <= bHighy){
                crossedArea[2] = aLowy;
                crossedArea[3] = aHighy;
            }
        }
        else if (aLowy <= bLowy && bLowy <= aHighy){
            if(bHighy <= aHighy){
                crossedArea[2] = bLowy;
                crossedArea[3] = bHighy;
            }
            else if(aHighy <= bHighy){
                crossedArea[2] = bLowy;
                crossedArea[3] = aHighy;
            }
        }
        else if(aHighy < bLowy){
            crossedArea[2] = 0;
            crossedArea[3] = 0;
        }else{
            crossedArea[3] = 0;
            crossedArea[4] = 0;
        }
        return crossedArea;
    }
}