package zzz;

public class RectanglePaking 
{
	 /// <summary>
    /// ��ʼ��ԴĤ��ʹ��ȫ��Ϊ��������
    /// </summary>
    /// <param name="plank">����ľ��
    /// plank��һ�����Ͷ�ά����
    public void InitPlank(int[][] plank)
    {
        for (int i = 0; i < plank.length; i++)
        {
            for (int j = 0; j < plank[0].length; j++)
                plank[i][j] = 0;
        }
    }

    /// <summary>
    /// ��ӡľ��ķ������
    /// </summary>
    /// <param name="plank">����ľ��
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
    /// �������ˮƽ�ߵ���ʼ���꣬�ж��Ƿ���Բ��룬�����������룬����ѡ����һ�������ж�
    /// </summary>
    /// <param name="plank">����ľ��
    /// <param name="W">��i�����εĿ��
    /// <param name="L">��i�����εĳ���
    /// <param name="index">�⼯�еĵ�i������
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
                    int zeroL = 0;//�հ�λ�ó���
                    int zeroW = 0;//�հ�λ�ÿ��
                    lowLW[0] = i;//���ˮƽ��������
                    lowLW[1] = j;//���ˮƽ�ߺ�����
                    for (int first_not_zero_L = lowLW[1]; first_not_zero_L < plank[0].length; first_not_zero_L++)  //����հ�λ�ó���
                    {
                        if (plank[lowLW[0]][first_not_zero_L] == 0)
                        {
                            zeroL++;
                        }
                        else break;
                    }

                    for (int first_not_zero_W = lowLW[0]; first_not_zero_W < plank.length; first_not_zero_W++)  //����հ�λ�ÿ��
                    {
                        if (plank[first_not_zero_W][lowLW[1]] == 0)
                        {
                            zeroW++;
                        }
                        else break;
                    }

                    if ((zeroW >= W) && (zeroL >= L))//����հ׵ط������Է���
                    {
                        flag = true;
                        for (int m = lowLW[0]; m < lowLW[0] + W; m++)//�����β��뵽ľ����
                        {
                            for (int n = lowLW[1]; n < lowLW[1] + L; n++)
                                plank[m][n] = index + 1;
                            	//�þ����ڽ⼯�е�λ�ñ�ʾ
                            	//plank[m][n] = 8;
                        }
                    }
                    if (flag) //����Ѿ����룬������ѭ��
                        break;

                    if ((zeroW) >= L && (zeroL >= W))//����հ׵ط������ת���Կ��Է���
                    {
                        for (int m = lowLW[0]; m < lowLW[0] + L; m++)//�����β��뵽ľ����
                        {
                            for (int n = lowLW[1]; n < lowLW[1] + W; n++)
                                plank[m][n] = index + 1;
                            	//plank[m][n] = 8;
                        }
                        flag = true;
                    }
                    if (flag)//����Ѿ����룬������ѭ��
                        break;
                }
            }
            if (flag)//����Ѿ����룬������ѭ��
                break;
        }
        return flag;
    }

    /// <summary>
    /// ����ľ�岻Ϊ�յ����
    /// </summary>
    /// <param name="plank">����ľ��
    /// <returns>��Ϊ�յ����</returns>
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
    /// ��鵱ǰ�����Ƿ��ܲ���ľ��
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
                    int zeroL = 0;//�հ�λ�ó���
                    int zeroW = 0;//�հ�λ�ÿ��
                    lowLW[0] = i;//���ˮƽ��������
                    lowLW[1] = j;//���ˮƽ�ߺ�����
                    for (int first_not_zero_L = lowLW[1]; first_not_zero_L < plank[0].length; first_not_zero_L++)  //����հ�λ�ó���
                    {
                        if (plank[lowLW[0]][first_not_zero_L] == 0)
                        {
                            zeroL++;
                        }
                        else break;
                    }

                    for (int first_not_zero_W = lowLW[0]; first_not_zero_W < plank.length; first_not_zero_W++)  //����հ�λ�ÿ��
                    {
                        if (plank[first_not_zero_W][lowLW[1]] == 0)
                        {
                            zeroW++;
                        }
                        else break;
                    }
                    if ((zeroW) >= wide && (zeroL >= length))//����հ׵ط������Է���
                        return true;
                    if ((zeroW) >= length && (zeroL >= wide))//����հ׵ط������ת���Կ��Է���
                        return true;
                }
            }
        }
        return false;
    }

}
