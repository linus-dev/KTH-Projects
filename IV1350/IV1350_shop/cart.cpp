#include "include/cart.h"

void model::Cart::UpdateCart(std::string ean, unsigned short amount) {
  /* TODO: Everything */
  cart_.insert({ean, amount}); 
}

model::cartmap::const_iterator ItBegin() {
  return cart_.cbegin();
}

modle::cartmap::const_iterator ItEnd() {
  return cart_.cend();
}
