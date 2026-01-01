klas JavaneseCompiler
{
awam tetep kosong utama (String [] args)
{
    perpuluhan X1, Y1;
    nomborbulat I, J, K;

    X1 = 0;
    Y1 = 10;
    K = 10;
    J = -10;

    kanggo (I = 1; I + 10 <= K; I = I - 1)
    {
        nalika (K + I < 20)
        {
            J = I;

            lakoni
            {
                yen (X1 > Y1)
                    J = J - 2;
                liyane
                    J = J / 4;

                X1 = X1 * 2.0;
            }
            nalika (J >= 20);

            K = K + 3;
        }
    }
}
}
