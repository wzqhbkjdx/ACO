package zzz;

public class Rectangle 
{
	public int length;//���εĳ�
    public int wide;//���εĿ�
    public double area;//���������ͬʱҲ����������ֵ
    public double pheromone;//���ε�ǰ����Ϣ��
    public double prePheromone;//����ǰһ�ε���Ϣ��
    public Ant[] ants;//���ϼ���
    public Rectangle[] rectangles;//���μ���

    /// <summary>
    /// Rectangle���캯��
    /// </summary>
    /// <param name="ant">��Ⱥ
    /// <param name="rectangle">����
    /// <param name="maxLength">ľ�峤��
    /// <param name="maxWide">ľ����
    public Rectangle(Ant[] ants, Rectangle[] rectangles, int maxLength, int maxWide)
    {
        pheromone = 0;  //���ε�ǰ����Ϣ��
        this.ants = ants; //��Ⱥ
        this.rectangles = rectangles;//���μ���
        prePheromone = pheromone = 100;//��ʼ��Ϣ�ض����
    }

    /// <summary>
    /// ���¾ֲ���Ϣ��
    /// </summary>
    /// <param name="index">���α��
    /// <param name="basicPheromone">��������Ϣ�ص�λ
    /// <returns>��Ϣ��</returns>
    public double LocalUpdatePheromone(double basicPheromone,  int index)
    {
        //�ó���ǰ����ĿǰΪֹ�����ʵľ�������
        int count = 0;
        for (boolean temp : ants[index].selectedRectangle) //selectedRectangle�Ǹ�bool���͵ļ��ϣ��洢��ǰ�����Ѿ����ʵľ���
        {
            if (temp)
                count++;
        }
        pheromone = (1.0 - 0.9 * (double)count / (double)rectangles.length) * basicPheromone * (1.0 / ((double)length 
            * (double)wide)) + (1.0 - 0.5 * (1.0 / (double)ants.length)) * pheromone;
        return pheromone;
    }

    /// <summary>
    /// ����ȫ����Ϣ��
    /// </summary>
    /// <param name="LAST">��Ϣ�س־õ�ϵ��
    /// <param name="basicPheromone">��������Ϣ�ص�λ
    /// <param name="bestAnt">���Ž������
    public void GlobalUpdatePheromone(double LAST, double basicPheromone, Ant bestAnt)
    {
        //ֻ������ʹ��������ѡ��ľ��μ�������Ϣ������
        for (int i = 0; i < rectangles.length; i++)
        {
            if (bestAnt.selectedRectangle[i])
                rectangles[i].pheromone = (1.0 - LAST) * rectangles[i].pheromone + basicPheromone 
                    * bestAnt.usedArea / bestAnt.maxArea;
        }
    }

}










