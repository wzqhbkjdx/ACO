package zzz;

import java.io.PrintStream;

public class Aco 
{
	int maxLength = 120, maxWide = 5000;
    int maxArea = maxLength * maxWide;
    //int[] L = { 4, 30, 10, 1, 10, 9, 20, 12, 13, 14, 15, 50, 2, 3,  9,  15, 50, 2,  3,  9, 15 };
    //int[] W = { 4, 10, 14, 4, 5,  3, 5,  11, 5,  18, 12, 12, 5, 26, 31, 16, 5,  26, 31, 16,10 };
    //int[] L = {120, 115,112, 110, 105,100, 95, 92 ,90, 85,80, 70,  65, 60, 40};
    //int[] W = {514, 85, 612, 285, 60, 126, 170,220,183,13,643,648 ,854,328,83};
    int[] L = {12, 11,11, 11,12, 11,11, 11};
    int[] W = {51, 8, 61, 28,51, 8, 61, 28};
    double p = 0.0f;
	
	public void start() throws Exception
	{
		System.out.println("矩形排样开始计算：");
		long date_s = System.currentTimeMillis();
		//System.out.print(System.currentTimeMillis());
		int[][] plank = this.AntTubaSearch();//禁忌搜索+蚁群结合函数
        double smallRectS = 0;
        PrintStream ps = System.out;
        PrintStream out = new PrintStream("c:/test.txt");  
	    System.setOut(out); 
        for (int i = 0; i < plank.length; i++)
        {
            for (int j = 0; j < plank[0].length; j++)
            {
            	System.out.print(plank[i][j] + " "); 
            	
                if (plank[i][j] != 0)
                    smallRectS++;
            }
            System.out.println("");
        }
        p = smallRectS / maxArea * 100;
        System.setOut(ps);
        long date_e = System.currentTimeMillis();
        System.out.println("计算结束。");
        long time_spend = date_e - date_s;
		//System.out.print(System.currentTimeMillis());
        System.out.println("面积利用率为：" + p +"%");
		System.out.print("共耗时：" + (time_spend/1000) + "秒");
        
	}
	
	//蚁群+禁忌搜索
	private int[][] AntTubaSearch() throws Exception
    {
      
        double ALPHA = 1.0;//控制参数
        double BETA = 1.0;//控制参数
        double Q0 = 0.6;//
        double LAST = 0.5;//
        double Unit = 100;//初始基础信息素100
        int NC = 10;//蚁群搜索迭代次数
        Print p = new Print();

        //设定被裁切源膜的长和宽
        int[][] plank = new int[maxWide][];
        for (int i = 0; i < maxWide; i++)
        {
            plank[i] = new int[maxLength];
        }
        
        //初始化源膜 使其全部为空闲区域
        RectanglePaking rping = new RectanglePaking();
        rping.InitPlank(plank);
        
        //定义蚁群 定义订单薄膜数组 定义最优蚂蚁
        Ant[] ant = new Ant[5];
        Rectangle[] rectangles = new Rectangle[L.length];
        Ant bestAnt = new Ant(ant, rectangles, plank, maxLength, maxWide);

        //初始化矩形，初始化蚂蚁，并给订单薄膜的长和宽赋值，计算每个薄膜的面积
        for (int i = 0; i < rectangles.length; i++)
        {
            rectangles[i] = new Rectangle(ant, rectangles, maxLength, maxWide);
        }
        for (int i = 0; i < ant.length; i++)
        {
            ant[i] = new Ant(ant, rectangles, plank, maxLength, maxWide);
        }
        for (int i = 0; i < rectangles.length; i++)
        {
            //
            rectangles[i].length = L[i];
            rectangles[i].wide = W[i];

            rectangles[i].area = (double)rectangles[i].length * (double)rectangles[i].wide;//
        }


        //蚁群算法 迭代NC次
        for (int i = 0; i < NC; i++)
        {
            //
        	System.out.println("第" + i + "次循环");
        	//p.PP(plank);
        	
           for (int j = 0; j < ant.length; j++)
            {
                ant[j].InitAnt();//初始化蚂蚁
                ant[j].CountAllowed();//计算蚂蚁还允许选择的薄膜 存储在allowedRectangle[]这个数组中
            }
           
            int count;
            
            for (int j = 0; j < ant.length; j++)
            {
            	
            	System.out.println("	" + "第" + j + "只蚂蚁");
            	/*int[][] plankk = plank;
            	ant[j] = new Ant(ant, rectangles, plankk, j, i);
            	ant[j].InitAnt();
            	ant[j].CountAllowed();*/
                count = 0;
                if (ant[j].usedArea > maxArea)
                    continue;
                for (int k = 0; k < rectangles.length; k++)
                {
                    int selection = -1;
                    selection = ant[j].NextSelection(ALPHA, BETA, Q0, k);
                    if (selection != -1)
                    {
                        rping.InsertRectangle(plank, W[selection], L[selection], selection);
                        ant[j].selectedRectangle[selection] = true;
                        ant[j].allowedRectangle[selection] = false;

                        ant[j].sequence[count++] = selection + 1;
                        ant[j].CountAllowed();//
                        rectangles[selection].LocalUpdatePheromone(Unit, j);//更新局部信息素
                        this.UpdateValue(ant, rectangles);//更新插入后的占用面积
                    }
                }

                for (int k = 0; k < rectangles.length; k++)
                {
                    if (!ant[j].selectedRectangle[k] && count != rectangles.length - 1)
                        ant[j].sequence[count++] = k + 1;
                }

                //
                if (bestAnt.usedArea < ant[j].usedArea)
                {
                    bestAnt.usedArea = ant[j].usedArea;
                    for (int q = 0; q < rectangles.length; q++)
                    {
                        bestAnt.selectedRectangle[q] = ant[j].selectedRectangle[q];
                        bestAnt.sequence[q] = ant[j].sequence[q];
                    }
                }
            }
            rectangles[0].GlobalUpdatePheromone(LAST, Unit, bestAnt);//
            //Console.WriteLine("NO.{0} bestArea:{1}", i, bestAnt.usedArea);
           
        }
        //Console.WriteLine("Final Best Area:{0}", bestAnt.usedArea);
        
       // p.PP(plankk);
        
        for (int i = 0; i < L.length; i++)
        {
            //Console.WriteLine(bestAnt.sequence[i]);
        }
        TabuSearch tabu = new TabuSearch();

        int[] solution = new int[L.length];//
        for (int i = 0; i < L.length; i++)
        {
            solution[i] = bestAnt.sequence[i];
        }

        //
        tabu.set_L(L);
        tabu.set_W(W);

        int[][] tabuTable = new int[L.length][];//
        int tabuLength = L.length; //

        //
        for (int i = 0; i < tabuTable.length; i++)
        {
            tabuTable[i] = new int[L.length + 2];
            for (int j = 0; j < tabuTable[0].length; j++)
            {
                tabuTable[i][j] = 0;
            }
        }
        int index = 0;

        rping.PrintPlank(plank);
        
        System.out.println("将最优解放入禁忌表");
        TabuSearch.InsertTabuTable(solution, tabuTable, rping.get_no_zero(plank), 0, tabuLength);  //
        System.out.println("循环计算");
        TabuSearch.ComputeLoop(solution, L, W, rping, plank, index, tabuTable, tabuLength);

        //TabuSearch.PrintfSolution(tabuTable, L, W, rping, plank);
       // Console.ReadLine();

        return plank;
    }

    private void UpdateValue(Ant[] ant, Rectangle[] rectangles)
    {
        for (int i = 0; i < ant.length; i++)
        {
            ant[i].usedArea = 0;
            for (int j = 0; j < rectangles.length; j++)
            {
                if (ant[i].selectedRectangle[j])
                {
                    ant[i].usedArea += rectangles[j].length * rectangles[j].wide;
                }
            }
        }
    }
	

}
