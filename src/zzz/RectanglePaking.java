package zzz;

public class RectanglePaking 
{
	 /// <summary>
    /// 初始化源膜，使其全部为空闲区域
    /// </summary>
    /// <param name="plank">矩形木板
    /// plank是一个整型二维数组
    public void InitPlank(int[][] plank)
    {
        for (int i = 0; i < plank.length; i++)
        {
            for (int j = 0; j < plank[0].length; j++)
                plank[i][j] = 0;
        }
    }

    /// <summary>
    /// 打印木块的放置情况
    /// </summary>
    /// <param name="plank">矩形木板
    public void PrintPlank(int[][] plank)
    {
       /* for (int i = 0; i < plank.length; i++)
        {
            for (int j = 0; j < plank[0].length; j++)
;
;
        }*/
    }

    /// <summary>
    /// 计算最低水平线的起始坐标，判断是否可以插入，如果可以则插入，否则选择下一个矩形判断
    /// </summary>
    /// <param name="plank">矩形木板
    /// <param name="W">第i件矩形的宽度
    /// <param name="L">第i件矩形的长度
    /// <param name="index">解集中的第i个矩形
    public boolean InsertRectangle(int[][] plank, int W, int L, int index)
    {
        int[] lowLW = new int[2];
        boolean flag = false;
        
        for (int i = 0; i < plank.length; i++)
        {
            for (int j = 0; j < plank[0].length; j++)
            {
                if (plank[i][j] == 0)
                {
                    int zeroL = 0;//空白位置长度
                    int zeroW = 0;//空白位置宽度
                    lowLW[0] = i;//最低水平线纵坐标
                    lowLW[1] = j;//最低水平线横坐标
                    for (int first_not_zero_L = lowLW[1]; first_not_zero_L < plank[0].length; first_not_zero_L++)  //计算空白位置长度
                    {
                        if (plank[lowLW[0]][first_not_zero_L] == 0)
                        {
                            zeroL++;
                        }
                        else break;
                    }

                    for (int first_not_zero_W = lowLW[0]; first_not_zero_W < plank.length; first_not_zero_W++)  //计算空白位置宽度
                    {
                        if (plank[first_not_zero_W][lowLW[1]] == 0)
                        {
                            zeroW++;
                        }
                        else break;
                    }

                    if ((zeroW >= W) && (zeroL >= L))//如果空白地方物块可以放入
                    {
                        flag = true;
                        for (int m = lowLW[0]; m < lowLW[0] + W; m++)//将矩形插入到木板中
                        {
                            for (int n = lowLW[1]; n < lowLW[1] + L; n++)
                                plank[m][n] = index + 1;
                            	//用矩形在解集中的位置表示
                            	//plank[m][n] = 8;
                        }
                    }
                    if (flag) //如果已经插入，跳出本循环
                        break;

                    if ((zeroW) >= L && (zeroL >= W))//如果空白地方物块旋转可以可以放入
                    {
                        for (int m = lowLW[0]; m < lowLW[0] + L; m++)//将矩形插入到木板中
                        {
                            for (int n = lowLW[1]; n < lowLW[1] + W; n++)
                                plank[m][n] = index + 1;
                            	//plank[m][n] = 8;
                        }
                        flag = true;
                    }
                    if (flag)//如果已经插入，跳出本循环
                        break;
                }
            }
            if (flag)//如果已经插入，跳出本循环
                break;
        }
        return flag;
    }

    /// <summary>
    /// 返回木板不为空的面积
    /// </summary>
    /// <param name="plank">矩形木板
    /// <returns>不为空的面积</returns>
    public int get_no_zero(int[][] plank)
    {
        int no_zero_count = 0;
        for (int i = 0; i < plank.length; i++)
        {
            for (int j = 0; j < plank[0].length; j++)
            {
                if (plank[i][j] != 0)
                    no_zero_count++;
            }
        }
        return no_zero_count;
    }

    /// <summary>
    /// 检查当前矩形是否能插入木板
    /// </summary>
    /// <param name="plank">
    /// <param name="W">
    /// <param name="L">
    /// <returns></returns>
    public boolean CanInsert(int[][] plank, int wide, int length)
    {
        int[] lowLW = new int[2];
        
        for (int i = 0; i < plank.length; i++)
        {
            for (int j = 0; j < plank[0].length; j++)
            {
                if (plank[i][j] == 0)
                {
                    int zeroL = 0;//空白位置长度
                    int zeroW = 0;//空白位置宽度
                    lowLW[0] = i;//最低水平线纵坐标
                    lowLW[1] = j;//最低水平线横坐标
                    for (int first_not_zero_L = lowLW[1]; first_not_zero_L < plank[0].length; first_not_zero_L++)  //计算空白位置长度
                    {
                        if (plank[lowLW[0]][first_not_zero_L] == 0)
                        {
                            zeroL++;
                        }
                        else break;
                    }

                    for (int first_not_zero_W = lowLW[0]; first_not_zero_W < plank.length; first_not_zero_W++)  //计算空白位置宽度
                    {
                        if (plank[first_not_zero_W][lowLW[1]] == 0)
                        {
                            zeroW++;
                        }
                        else break;
                    }
                    if ((zeroW) >= wide && (zeroL >= length))//如果空白地方物块可以放入
                        return true;
                    if ((zeroW) >= length && (zeroL >= wide))//如果空白地方物块旋转可以可以放入
                        return true;
                }
            }
        }
        return false;
    }

}
