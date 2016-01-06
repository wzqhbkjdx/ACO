package zzz;

import java.util.Random;

public class Ant 
{
	public int usedArea;//�������и�����
    public boolean[] selectedRectangle;//��ǰ�����Ѿ�ѡ��ľ���
    public boolean[] allowedRectangle;//��ǰ��������ѡ��ľ���
    public int[] oldFitness;//��¼��ǰ2�ε���Ӧ��ֵ
    public Ant[] ant;//��Ⱥ
    public Rectangle[] rectangle;//����
    public int[][] plank;//ľ��
    public int maxArea;//ľ�����
    public int maxLength;//ľ�峤��
    public int maxWide;//ľ����
    public int[] sequence;//���μ��и�Ĵ���
    
    /// <summary>
    /// ���ϵĹ��캯��
    /// </summary>
    /// <param name="ant">��Ⱥ
    /// <param name="rectangle">����
    /// <param name="plank">ľ��
    /// <param name="maxLength">ľ�峤��
    /// <param name="maxWide">ľ����
    public Ant(Ant[] ant, Rectangle[] rectangle, int[][] plank, int maxLength, int maxWide)
    {
        usedArea = 0; //�������и�����
        this.ant = ant; //��������
        this.rectangle = rectangle;//��������
        this.plank = plank;//ľ������
        this.maxLength = maxLength;//ľ�峤��
        this.maxWide = maxWide;//ľ����
        maxArea = maxLength * maxWide;//ľ�����
        selectedRectangle = new boolean[rectangle.length];//��ǰ�����Ѿ�ѡ��ľ���
        allowedRectangle = new boolean[rectangle.length];//��ǰ���ϻ��ܹ�ѡ��ľ���
        oldFitness = new int[2];//��¼��ǰ2�ε���Ӧ��ֵ
        oldFitness[0] = 0;
        oldFitness[1] = 1;
       sequence  = new int[rectangle.length];//���μ��и�Ĵ���
       for (int i = 0; i < sequence.length; i++ )
       {
           sequence[i] = i + 1;
       }
    }

    /// <summary>
    /// ��ʼ�����ϵĽ�
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
    /// �������ϻ�����ѡ��ľ���
    /// </summary>
    public void CountAllowed()
    {
        RectanglePaking rping = new RectanglePaking();
        for (int i = 0; i < rectangle.length; i++)
        {
            //����Ƿ��ܲ���
            if (!selectedRectangle[i] && !rping.CanInsert(plank, rectangle[i].wide, rectangle[i].length))
                allowedRectangle[i] = false;
        }
    }

    /// <summary>
    /// �õ���ǰ���ϵ���Ӧ��ֵ,
    /// </summary>
    /// <returns>��Ӧ��ֵ</returns>
    public double FitnessValue()
    {
        double area = 0;
        for (int i = 0; i < rectangle.length; i++)
        {
            if (selectedRectangle[i])
                //��Ӧ��ֵ = ���μ��ĳ��� * ���μ��Ŀ��
                area += rectangle[i].length * rectangle[i].wide;
        }
        return area / maxArea;
    }

    /// <summary>
    /// ���ϵ���һ��ѡ��
    /// </summary>
    /// <param name="alpha">��ʾ�����ϵ���Ϣ��������ѡ����������ô�С
    /// <param name="beta">��ѡ����ε������̶ȵ����ô�С
    /// <param name="Q0">ȷ��ѡȡ��Ѹ���ĸ���
    /// <returns>ѡ��ľ��α��</returns>
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
