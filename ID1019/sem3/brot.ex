defmodule Brot do
  def mandelbrot(c, m) do
    z0 = Complex.new(0, 0);
    i = 0;
    test(i, z0, c, m)
  end

  def test(m, _, _, m) do
    0
  end

  def test(i, z, c, m) do
    cmplx_value = Complex.add(Complex.sqr(z), c);
    abs_value = Complex.abs(z);
    if (abs_value > 2) do
      i
    else
      test(i + 1, cmplx_value, c, m)
    end
  end
  
end
