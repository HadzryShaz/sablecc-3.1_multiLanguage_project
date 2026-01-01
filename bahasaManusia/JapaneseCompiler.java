kurasu JapaneseCompiler
{
koukai seiteki muko mein (String [] args)
{
    fudo X1, Y1;
    seisu I, J, K;

    X1 = 0;
    Y1 = 10;
    K = 10;
    J = -10;

    kurikaeshi (I = 1; I + 10 <= K; I = I - 1)
    {
        aida (K + I < 20)
        {
            J = I;

            jikko
            {
                moshi (X1 > Y1)
                    J = J - 2;
                hokani
                    J = J / 4;

                X1 = X1 * 2.0;
            }
            aida (J >= 20);

            K = K + 3;
        }
    }
}
}