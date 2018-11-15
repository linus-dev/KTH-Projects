from flask import Flask
from flask import render_template
app = Flask(__name__)

recipes_data = [
  {
    'dish': 'Space Balls',
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
    'servings': '8-10',
    'comments': [['Jackie', 'This was rad!']],
    'instruction': open('recipes/meatballs.txt', 'r').read(),
    'image': 'https://w0cosv3kke2wxd231fhcn6j9-wpengine.netdna-ssl.com/wp-content/uploads/2018/02/meatballs3.png'
  },
  {
    'dish': 'Wave Cakes',
    'ingredients': {
      'All-purpose flour': '1 1/2 cups',
      'Baking powder': '3 1/2 teaspoons',
      'Salt': '1 Teaspoon',
      'White sugar': '1 Tablespoon',
      'Milk': '1 1/4 cups',
      'Egg': 1,
      'Melted butter': '3 Tablespoons'
    },
    'prep': '10 minutes',
    'servings': '3-4',
    'comments': [['Tony', 'This was bangin\'!']],
    'instruction': open('recipes/pancakes.txt', 'r').read(),
    'image': 'https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/160/apple/81/pancakes_1f95e.png'
  },
  {
    'dish': 'Arcade Burger',
    'ingredients': {
      '10 Tablespoons Salted Butter Softened': '10 Tablespoons',
      'Medium Sweet Yellow Onion Chopped': 1,
      'Water': '1 Tablespoon',
      'Kosher Salt': '3/4 Teaspoon',
      'Freshly Ground Black Pepper': '3/4 Teaspoon',
      '90% Lean Ground Beef': '1 lb',
      'Hamburger Buns (toasted)': 4,
      'Vegetable oil': '1 Teaspoon',
      'American Cheese': '4 Slices'
    },
    'prep': '10 minutes',
    'servings': '3-4',
    'comments': [['Anna', 'THE BOMB!!']],
    'instruction': open('recipes/burger.txt', 'r').read(),
    'image': 'http://icons.iconarchive.com/icons/pixelkit/tasty-bites/256/hamburger-icon.png'
  }
]

@app.route('/ayy')
def ayy():
  return render_template('show.html')

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
