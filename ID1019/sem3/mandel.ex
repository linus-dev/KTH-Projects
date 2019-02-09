defmodule Mandel do
  def mandelbrot(width, height, x, y, k, depth) do
    trans = fn(w, h) ->
      Complex.new(x + k * (w - 1), y - k * (h - 1))
    end
    rows(width, height, trans, depth, [])
  end

  def rows(_, 0, _, _, rows) do
    rows
  end
  def rows(width, height, trans, depth, rows) do
    rows(width, height - 1, trans, depth, [row(width, height, trans, depth, []) | rows])
  end

  def row(0, _, _ ,_, row) do
    row
  end
  def row(w, h, tr, depth, row) do
    c = tr.(w, h);
    num = Brot.mandelbrot(c, depth);
    colour = Colour.convert(num, depth);
    row(w - 1, h, tr, depth, [colour | row])
  end

  def demo() do
    small(-2.6, 1.2, 1.6)
  end
  def small(x0, y0, xn) do
    width = 960
    height = 540
    depth = 64
    k = (xn - x0) / width
    image = Mandel.mandelbrot(width, height, x0, y0, k, depth)
    PPM.write("small.ppm", image)
  end
end
