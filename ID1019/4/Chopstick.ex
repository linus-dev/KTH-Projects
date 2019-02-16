defmodule Chopstick do
  def start() do
    stick = spawn_link(fn -> available() end)
    {:cs, stick}
  end

  def request({:cs, pid}, timeout) do
    send(pid, {:request, self()})
    receive do
      :avail -> :ok
    after timeout ->
      :no
    end
  end

  def quit({:cs, pid}) do
    send(pid, :quit);
  end
  
  def return({:cs, pid}) do
    send(pid, :return);
  end
  
  def available() do
    receive do
      {:request, from} ->
        send(from, :avail)
        gone()
      :quit -> :ok
    end
  end

  def gone() do
    receive do
      :return ->
        available()
      :quit -> :ok
    end
  end
end
