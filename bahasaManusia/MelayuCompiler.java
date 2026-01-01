darjah NewComp
{
umum pegun gelap utama (String [] args)
{
    ambang X1, Y1;
    angka I, J, K;

    X1 = 0;
    Y1 = 10;
    K = 10;
    J = -10;

    untuk (I = 1; I + 10 <= K; I = I - 1)
    {
        apabila (K + I < 20)
        {
            J = I;

            buat
            {
                jika (X1 > Y1)
                    J = J - 2;
                selain
                    J = J / 4;

                X1 = X1 * 2.0;
            }
            apabila (J >= 20);

            K = K + 3;
        }
    }
}
}
