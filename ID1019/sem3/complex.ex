defmodule Complex do
  def new(x, y) do
    {:complex, x, y}
  end
  # ADD THEM!
  def add({:complex, x1, y1}, {:complex, x2, y2}) do
    {:complex, x1 + x2, y1 + y2}
  end
  # Square complex number.
  def sqr({:complex, x, y}) do
    {:complex, x*x - y*y, 2*x*y}
  end
  # Absolute value of complex number.
  def abs({:complex, x, y}) do
    :math.sqrt(x*x + y*y)
  end 
end
