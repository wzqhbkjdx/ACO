package zzz;

public class TabuSearch 
{
	private int[] L;
    private int[] W;

    /**************
     * 给解集的L赋值
     * @param s_L:解集的L(长度)
     */

    public void set_L(int[] s_L)
    {
        L = new int[s_L.length];
        for (int i = 0; i < s_L.length; i++)
        {
            L[i] = s_L[i];
        }
    }

    /**************
     * 给解集的W（宽度）赋值
     * @param s_W:解集的L(宽度)
     */
    public void set_W(int[] s_W)
    {
        W = new int[s_W.length];
        for (int i = 0; i < s_W.length; i++)
        {
            W[i] = s_W[i];
        }
    }

    /*********
    * 将最优解放入禁忌表中
    * 入口参数
    * 目标解：int x[]
    * 禁忌表：int tabuTable[][]
    * 目标解的总价值：int value
    * 第几次插入：int k   (禁忌表中的第K行)
    * count 禁忌长度
    */

    static public int[][] InsertTabuTable(int[] x, int[][] tabuTable, int value, int k, int count)
    {
        int n = x.length;
        int j_n = tabuTable[0].length;   //禁忌表列数

        for (int i = 0; i < tabuTable.length; i++)          //替换禁忌长度为0的行
        {
            if (tabuTable[i][j_n - 1] == 0)
                k = i;
        }

        for (int i = 0; i < x.length; i++)            //将解放入禁忌表
        {
            tabuTable[k][i] = x[i];
        }
        tabuTable[k][n] = value;

        tabuTable[k][n + 1] = count;					//设定禁忌长度

        for (int j = tabuTable.length - 1; j >= 0; j--)              //禁忌表长度减一
        {
            if (tabuTable[j][n + 1] == 0)
                tabuTable[j][n + 1] = 0;
            else
                tabuTable[j][n + 1]--;
        }
        return tabuTable;
    }


    /*************这里用了蛮力法，日后可以改善
     * 判断是否放入禁忌表中
     * 如果禁忌表中不含有特解，返回true,可以插入禁忌表中
     * 
     */

    public static boolean if_insertJinji(int[] temp_x, int[][] tabuTable)
    {
        boolean flag = true;


        for (int i = 0; i < tabuTable.length; i++)
        {
            for (int j = 0; j < temp_x.length; j++)
            {
                if (tabuTable[i][j] != temp_x[j])
                    break;
                else if (j == (temp_x.length - 1))
                    flag = false;
            }
            if (!flag)
                break;
        }
        return flag;
    }


    /********
     * 判断禁忌表中W最大的解，并打印解
     * @param tabuTable
     */
    public static void PrintfSolution(int[][] tabuTable, int[] L, int[] W, RectanglePaking rping, int[][] plank)
    {
        rping.InitPlank(plank);// 木板初始化
        int x = 0;	//表示最优解所处在的列
        int lieshu = tabuTable[0].length;
        int max = tabuTable[0][lieshu - 2];
        for (int i = 1; i < tabuTable.length; i++)
        {
            if (tabuTable[i][lieshu - 2] > max)
            {
                max = tabuTable[i][lieshu - 2];
                x = i;
            }
        }

// Console.WriteLine("最大的价值为： " + max);
        for (int i = 0; i < lieshu; i++)
           // Console.Write(tabuTable[x][i] + " ");
// Console.WriteLine();


        for (int k = 0; k < tabuTable[0].length - 2; k++)           //将初始解放入木块中
        {
            rping.InsertRectangle(plank, W[tabuTable[x][k] - 1], L[tabuTable[x][k] - 1], k);
        }
        rping.PrintPlank(plank);
    }


    /*******
    * 计算邻域解，求禁忌最优解
    * @param x 目标解集
    * @param L 物品的长度（横坐标）
    * @param W 物品的宽度（纵坐标）
    * @param rping 排样对象，包含排样的一系列操作
    * @param plank 木板。用矩阵表示
    * @param tabuTable 禁忌表
    * 
    */
    public static int[] ComputeNeighborhood(int[] x, int[] L, int[] W, RectanglePaking rping, int[][] plank, int[][] tabuTable)
    {
        int max_w = 0;
        int[] temp_x = new int[x.length];
        int[] Knapsack_table = new int[x.length];
        //System.out.println(x.length);
        for (int mn = 1; mn < x.length; mn++)
        {
            for (int mq = 0; mq < x.length; mq++)   //每次循环，temp_x[]=x[];
            {
                temp_x[mq] = x[mq];
            }
            int temp; //交换i,j位置上的物品
            temp = temp_x[0];
            temp_x[0] = temp_x[mn];
            temp_x[mn] = temp;

            rping.InitPlank(plank);// 木板初始化
            System.out.print(".");
            for (int k = 0; k < x.length; k++)
            {
                rping.InsertRectangle(plank, W[temp_x[k] - 1], L[temp_x[k] - 1], k);
            }
            System.out.print(".");
            int w_sum = rping.get_no_zero(plank);
            System.out.print(".");
            if (if_insertJinji(temp_x, tabuTable) && (w_sum > max_w))   //解不在禁忌表中且面积最大
            {
                max_w = w_sum;
                for (int m = 0; m < x.length; m++)
                {
                    Knapsack_table[m] = temp_x[m];
                }
            }
            System.out.print(".");
            //System.out.println(" ");
        }

        return Knapsack_table;
    }

    /************
     * 计算出总价值，返回占木块的总面积
     * @param x 初始解
     * @return value 
     */
    public static int value(RectanglePaking rping, int[][] plank, int[] temp_x, int[] L, int[] W)
    {
        int value = 0;
        rping.InitPlank(plank);
        for (int k = 0; k < temp_x.length; k++)
        {
            rping.InsertRectangle(plank, W[temp_x[k] - 1], L[temp_x[k] - 1], k);
        }
        value = rping.get_no_zero(plank);
        return value;
    }

    /*********
       * 不断循环计算邻域，并插入禁忌表
       * @param x
       * @param v
       * @param w
       * @param vall
       * @param tabuTable
       * @param to_x
       */
    public static void ComputeLoop(int[] x, int[] L, int[] W, RectanglePaking rping, int[][] plank, int cishu, int[][] tabuTable, int count)
    {
        int[] chushi_linyu = new int[x.length];
        int[] linyu = new int[x.length];
        int val = 0;
        //System.out.println(x.length);
        for (int c = 0; c < x.length; c++)
        {
        	 chushi_linyu[c] = x[c];
        }
           
        
        //System.out.println(1);
        
        for (int i = 0, k = 1; i < 5; i++)
        {
        	System.out.print("循环第" + i +"次");
            for (int c = 0; c < x.length; c++)
                linyu[c] = chushi_linyu[c];
            
            //System.out.println(2);
            
            rping.InitPlank(plank);

            for (int kk = 0; kk < x.length; kk++)           //将初始解放入木块中
            {
                rping.InsertRectangle(plank, W[linyu[kk] - 1], L[linyu[kk] - 1], kk);
            }
            //System.out.println(3);

            val = value(rping, plank, linyu, L, W); 	//计算背包里的总价值	
            InsertTabuTable(linyu, tabuTable, val, k++, count);
            //System.out.println(4);
            chushi_linyu = ComputeNeighborhood(linyu, L, W, rping, plank, tabuTable);     //计算领域
            System.out.println("循环第" + i +"次结束");
        }
        
        System.out.println("循环结束");
    }

}
