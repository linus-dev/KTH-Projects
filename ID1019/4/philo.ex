defmodule Philosopher do
  def sleep(0) do
    :ok
  end
  def sleep(t) do
    :timer.sleep(:rand.uniform(t));
  end

  def start(hunger, right, left, name, ctrl) do
    philosopher = spawn_link(fn -> 
      dream(hunger, right, left, name, ctrl)
    end);
    philosopher
  end

  def dream(0, _right, _left, name, ctrl) do
    IO.puts("#{name} is done.");
    send(ctrl, :done);
  end
  
  def dream(hunger, right, left, name, ctrl) do
    sleep(1000);
    IO.puts("#{name} Dreaming.");
    wait(hunger, right, left, name, ctrl);
  end

  def wait(hunger, right, left, name, ctrl) do
    IO.puts("#{name} Waiting.");
    sleep(1000);
    case Chopstick.request(right, 5000) do
      :ok ->
        sleep(1000);
        case Chopstick.request(left, 5000) do
          :ok -> eat(hunger, right, left, name, ctrl);
          :no ->
            wait(hunger, right, left, name, ctrl);
        end
      :no ->
        wait(hunger, right, left, name, ctrl);
    end 
  end

  def eat(hunger, right, left, name, ctrl) do
    IO.puts("#{name} Eating.");
    sleep(1000);
    Chopstick.return(right);
    Chopstick.return(left);
    dream(hunger - 1, right, left, name, ctrl);
  end 
end

