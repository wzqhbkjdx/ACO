package zzz;

public class Rectangle 
{
	public int length;//矩形的长
    public int wide;//矩形的宽
    public double area;//矩形面积，同时也是它的期望值
    public double pheromone;//矩形当前的信息素
    public double prePheromone;//矩形前一次的信息素
    public Ant[] ants;//蚂蚁集合
    public Rectangle[] rectangles;//矩形集合

    /// <summary>
    /// Rectangle构造函数
    /// </summary>
    /// <param name="ant">蚁群
    /// <param name="rectangle">矩形
    /// <param name="maxLength">木板长度
    /// <param name="maxWide">木板宽度
    public Rectangle(Ant[] ants, Rectangle[] rectangles, int maxLength, int maxWide)
    {
        pheromone = 0;  //矩形当前的信息素
        this.ants = ants; //蚁群
        this.rectangles = rectangles;//矩形集合
        prePheromone = pheromone = 100;//初始信息素都相等
    }

    /// <summary>
    /// 更新局部信息素
    /// </summary>
    /// <param name="index">矩形编号
    /// <param name="basicPheromone">基本的信息素单位
    /// <returns>信息素</returns>
    public double LocalUpdatePheromone(double basicPheromone,  int index)
    {
        //得出当前蚂蚁目前为止所访问的矩形总数
        int count = 0;
        for (boolean temp : ants[index].selectedRectangle) //selectedRectangle是个bool类型的集合，存储当前蚂蚁已经访问的矩形
        {
            if (temp)
                count++;
        }
        pheromone = (1.0 - 0.9 * (double)count / (double)rectangles.length) * basicPheromone * (1.0 / ((double)length 
            * (double)wide)) + (1.0 - 0.5 * (1.0 / (double)ants.length)) * pheromone;
        return pheromone;
    }

    /// <summary>
    /// 更新全局信息素
    /// </summary>
    /// <param name="LAST">信息素持久的系数
    /// <param name="basicPheromone">基本的信息素单位
    /// <param name="bestAnt">最优解的蚂蚁
    public void GlobalUpdatePheromone(double LAST, double basicPheromone, Ant bestAnt)
    {
        //只对最优使用率蚂蚁选择的矩形件进行信息量更新
        for (int i = 0; i < rectangles.length; i++)
        {
            if (bestAnt.selectedRectangle[i])
                rectangles[i].pheromone = (1.0 - LAST) * rectangles[i].pheromone + basicPheromone 
                    * bestAnt.usedArea / bestAnt.maxArea;
        }
    }

}










