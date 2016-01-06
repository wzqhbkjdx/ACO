package zzz;

public class TabuSearch 
{
	private int[] L;
    private int[] W;

    /**************
     * ���⼯��L��ֵ
     * @param s_L:�⼯��L(����)
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
     * ���⼯��W����ȣ���ֵ
     * @param s_W:�⼯��L(���)
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
    * �����Ž������ɱ���
    * ��ڲ���
    * Ŀ��⣺int x[]
    * ���ɱ�int tabuTable[][]
    * Ŀ�����ܼ�ֵ��int value
    * �ڼ��β��룺int k   (���ɱ��еĵ�K��)
    * count ���ɳ���
    */

    static public int[][] InsertTabuTable(int[] x, int[][] tabuTable, int value, int k, int count)
    {
        int n = x.length;
        int j_n = tabuTable[0].length;   //���ɱ�����

        for (int i = 0; i < tabuTable.length; i++)          //�滻���ɳ���Ϊ0����
        {
            if (tabuTable[i][j_n - 1] == 0)
                k = i;
        }

        for (int i = 0; i < x.length; i++)            //���������ɱ�
        {
            tabuTable[k][i] = x[i];
        }
        tabuTable[k][n] = value;

        tabuTable[k][n + 1] = count;					//�趨���ɳ���

        for (int j = tabuTable.length - 1; j >= 0; j--)              //���ɱ��ȼ�һ
        {
            if (tabuTable[j][n + 1] == 0)
                tabuTable[j][n + 1] = 0;
            else
                tabuTable[j][n + 1]--;
        }
        return tabuTable;
    }


    /*************�����������������պ���Ը���
     * �ж��Ƿ������ɱ���
     * ������ɱ��в������ؽ⣬����true,���Բ�����ɱ���
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
     * �жϽ��ɱ���W���Ľ⣬����ӡ��
     * @param tabuTable
     */
    public static void PrintfSolution(int[][] tabuTable, int[] L, int[] W, RectanglePaking rping, int[][] plank)
    {
        rping.InitPlank(plank);// ľ���ʼ��
        int x = 0;	//��ʾ���Ž������ڵ���
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

// Console.WriteLine("���ļ�ֵΪ�� " + max);
        for (int i = 0; i < lieshu; i++)
           // Console.Write(tabuTable[x][i] + " ");
// Console.WriteLine();


        for (int k = 0; k < tabuTable[0].length - 2; k++)           //����ʼ�����ľ����
        {
            rping.InsertRectangle(plank, W[tabuTable[x][k] - 1], L[tabuTable[x][k] - 1], k);
        }
        rping.PrintPlank(plank);
    }


    /*******
    * ��������⣬��������Ž�
    * @param x Ŀ��⼯
    * @param L ��Ʒ�ĳ��ȣ������꣩
    * @param W ��Ʒ�Ŀ�ȣ������꣩
    * @param rping �������󣬰���������һϵ�в���
    * @param plank ľ�塣�þ����ʾ
    * @param tabuTable ���ɱ�
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
            for (int mq = 0; mq < x.length; mq++)   //ÿ��ѭ����temp_x[]=x[];
            {
                temp_x[mq] = x[mq];
            }
            int temp; //����i,jλ���ϵ���Ʒ
            temp = temp_x[0];
            temp_x[0] = temp_x[mn];
            temp_x[mn] = temp;

            rping.InitPlank(plank);// ľ���ʼ��
            System.out.print(".");
            for (int k = 0; k < x.length; k++)
            {
                rping.InsertRectangle(plank, W[temp_x[k] - 1], L[temp_x[k] - 1], k);
            }
            System.out.print(".");
            int w_sum = rping.get_no_zero(plank);
            System.out.print(".");
            if (if_insertJinji(temp_x, tabuTable) && (w_sum > max_w))   //�ⲻ�ڽ��ɱ�����������
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
     * ������ܼ�ֵ������ռľ��������
     * @param x ��ʼ��
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
       * ����ѭ���������򣬲�������ɱ�
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
        	System.out.print("ѭ����" + i +"��");
            for (int c = 0; c < x.length; c++)
                linyu[c] = chushi_linyu[c];
            
            //System.out.println(2);
            
            rping.InitPlank(plank);

            for (int kk = 0; kk < x.length; kk++)           //����ʼ�����ľ����
            {
                rping.InsertRectangle(plank, W[linyu[kk] - 1], L[linyu[kk] - 1], kk);
            }
            //System.out.println(3);

            val = value(rping, plank, linyu, L, W); 	//���㱳������ܼ�ֵ	
            InsertTabuTable(linyu, tabuTable, val, k++, count);
            //System.out.println(4);
            chushi_linyu = ComputeNeighborhood(linyu, L, W, rping, plank, tabuTable);     //��������
            System.out.println("ѭ����" + i +"�ν���");
        }
        
        System.out.println("ѭ������");
    }

}
