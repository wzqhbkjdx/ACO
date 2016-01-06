package zzz;

import java.util.Random;

public class Ant 
{
	public int usedArea;//蚂蚁已切割的面积
    public boolean[] selectedRectangle;//当前蚂蚁已经选择的矩形
    public boolean[] allowedRectangle;//当前蚂蚁允许选择的矩形
    public int[] oldFitness;//记录以前2次的适应度值
    public Ant[] ant;//蚁群
    public Rectangle[] rectangle;//矩形
    public int[][] plank;//木板
    public int maxArea;//木板面积
    public int maxLength;//木板长度
    public int maxWide;//木板宽度
    public int[] sequence;//矩形件切割的次序
    
    /// <summary>
    /// 蚂蚁的构造函数
    /// </summary>
    /// <param name="ant">蚁群
    /// <param name="rectangle">矩形
    /// <param name="plank">木板
    /// <param name="maxLength">木板长度
    /// <param name="maxWide">木板宽度
    public Ant(Ant[] ant, Rectangle[] rectangle, int[][] plank, int maxLength, int maxWide)
    {
        usedArea = 0; //蚂蚁已切割的面积
        this.ant = ant; //蚂蚁数组
        this.rectangle = rectangle;//矩形数组
        this.plank = plank;//木板数组
        this.maxLength = maxLength;//木板长度
        this.maxWide = maxWide;//木板宽度
        maxArea = maxLength * maxWide;//木板面积
        selectedRectangle = new boolean[rectangle.length];//当前蚂蚁已经选择的矩形
        allowedRectangle = new boolean[rectangle.length];//当前蚂蚁还能够选择的矩形
        oldFitness = new int[2];//记录以前2次的适应度值
        oldFitness[0] = 0;
        oldFitness[1] = 1;
       sequence  = new int[rectangle.length];//矩形件切割的次序
       for (int i = 0; i < sequence.length; i++ )
       {
           sequence[i] = i + 1;
       }
    }

    /// <summary>
    /// 初始化蚂蚁的解
    /// </summary>
    public void InitAnt()
    {
        usedArea = 0;
        for (int i = 0; i < rectangle.length; i++)
        { 
            selectedRectangle[i] = false;
            allowedRectangle[i] = true;
        }
    }

    /// <summary>
    /// 计算蚂蚁还允许选择的矩形
    /// </summary>
    public void CountAllowed()
    {
        RectanglePaking rping = new RectanglePaking();
        for (int i = 0; i < rectangle.length; i++)
        {
            //检查是否能插入
            if (!selectedRectangle[i] && !rping.CanInsert(plank, rectangle[i].wide, rectangle[i].length))
                allowedRectangle[i] = false;
        }
    }

    /// <summary>
    /// 得到当前蚂蚁的适应度值,
    /// </summary>
    /// <returns>适应度值</returns>
    public double FitnessValue()
    {
        double area = 0;
        for (int i = 0; i < rectangle.length; i++)
        {
            if (selectedRectangle[i])
                //适应度值 = 矩形件的长度 * 矩形件的宽度
                area += rectangle[i].length * rectangle[i].wide;
        }
        return area / maxArea;
    }

    /// <summary>
    /// 蚂蚁的下一步选择
    /// </summary>
    /// <param name="alpha">表示矩形上的信息量对蚂蚁选择所起的作用大小
    /// <param name="beta">表选择矩形的期望程度的作用大小
    /// <param name="Q0">确定选取最佳个体的概率
    /// <returns>选择的矩形编号</returns>
    public int NextSelection(double alpha, double beta, double Q0, int index)
    {
        double max = 0.0;
        double temp = 0.0;
        int selection = -1;
        Random rd = new Random();
        double p = rd.nextDouble();
        
        if (p < Q0)
        {
            for (int i = 0; i < rectangle.length; i++)
            {
                if (allowedRectangle[i])
                {
                    temp = Math.pow(rectangle[i].pheromone, alpha) * Math.pow(rectangle[i].area, beta);
                    if (max < temp)
                    {
                        max = temp;
                        selection = i;
                    }
                }
            }
        }
        else
        {
            double total = 0.0;
            double[] ph = new double[rectangle.length];
            double[] p2 = new double[rectangle.length];
            for (int i = 0; i < rectangle.length; i++)
            {
                if (allowedRectangle[i])
                {
                    ph[i] = Math.pow(rectangle[i].pheromone, alpha) * Math.pow(rectangle[i].area, beta);
                    total += ph[i];
                }
                else ph[i] = 0.0;
            }
            for (int i = 0; i < rectangle.length; i++)
            {
                p2[i] = ph[i] / total;
            }
            p = rd.nextDouble();
            for (int i = 0; i < rectangle.length; i++)
            {
                p -= p2[i];
                if (p < 0)
                {
                    selection = i;
                    break;
                }
            }
        }
        return selection;
    }

}
