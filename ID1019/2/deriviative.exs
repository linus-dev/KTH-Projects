defmodule Derivative do
  @type constant() :: {:const, number()} | {:const, atom()};
  @type literal() :: {:const, number()} | {:const, atom()} | {:var, atom()};
  @type expr() :: {:add, expr(), expr()} | {:mul, expr(), expr()} | literal();

  def deriv({:const, _}, _) do
    {:const, 0}
  end
  def deriv({:var, v}, v) do
    {:const, 1}
  end
  def deriv({:var, y}, _) do
    {:var, y}
  end
  def deriv({:mul, e1, e2}, v) do
    {:add, {:mul, deriv(e1, v), e2}, {:mul, e1, deriv(e2, v)}} 
  end
  def deriv({:add, e1, e2}, v) do
    {:add, deriv(e1, v), deriv(e2, v)}
  end
  def deriv({:exp, {:var, x}, {:const, n}}, x) do
    {:mul, {:const, n}, {:exp, {:var, x}, {:const, n - 1}}}
  end
  def deriv({:ln, x}, x) do
    {:exp, {:var, x}, -1}
  end
  def deriv({:mul, c, {:exp, {:var, x}, {:const, n}}}, x) do
    {:mul, {:const, c}, deriv({:exp, {:var, x}, {:const, n}}, x)}
  end
end
