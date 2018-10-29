from flask import Flask
from flask import render_template
app = Flask(__name__)

recipes_data = [
  {
    'dish': 'Meatballs',
    'ingredients': {
      'Milk': '1/4 cup',
      'Panko breadcrumbs': '1/4 cup',
      'Ground chicken': '1 1/2 lbs',
      'Cloves garlic, minced': 2,
      'Minced fresh ginger': '2 Teaspoons',
      'Minced scallions': '2 Tablespoons',
      'Low sodium soy sauce': '2 Tablespoons',
      'Salt': '1/4 Teaspoon',
      'Black pepper': '1/4 Teaspoon'
    },
    'prep': '8 minutes',
    'servings': 10,
    'instruction': open('recipes/meatballs.txt', 'r').read(),
    'image': 'https://www.justataste.com/wp-content/uploads/2013/07/baked-orange-chicken-meatballs-recipe.jpg'
  },
  {
    'dish': 'Pancakes',
    'ingredients': {
      'All-purpose flour': '1 1/2 cups',
      'Baking powder': '3 1/2 teaspoons',
      'Salt': '1 Teaspoon',
      'White sugar': '1 Tablespoon',
      'Milk': '1 1/4 cups',
      'Egg': 1,
      'Melted butter': '3 Tablespoons'
    },
    'prep': '9999 hours',
    'servings': '100-200 pesos',
    'instruction': open('recipes/pancakes.txt', 'r').read(),
    'image': 'https://images.media-allrecipes.com/userphotos/720x405/4948036.jpg'
  }
]

@app.route('/')
def hello_world():
  return render_template('index.html')

@app.route('/calendar')
def calendar():
  return render_template('calendar.html', recipes_data=recipes_data)

@app.route('/recipes')
def recipes():
  return render_template('recipes.html', recipes_data=recipes_data)

@app.route('/recipes/<int:recipe>')
def show_recipe(recipe):
  return render_template('recipe.html', recipe = recipes_data[recipe]);
