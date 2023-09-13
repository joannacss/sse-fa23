from flask import Flask, render_template, request
import xml.etree.ElementTree as ET
import xml.dom.minidom


app = Flask(__name__)


@app.route('/')
def index():
    return render_template("index.html")

@app.route('/submit', methods=['POST'])
def submit():
    # Get data from form
    username = request.form.get('username')
    email = request.form.get('email')
    password = request.form.get('password')

    # Parse XML file
    tree = ET.parse('users.xml')
    root = tree.getroot()

    # Create new user entry
    user = ET.SubElement(root, 'user')
    ET.SubElement(user, 'uname').text = username
    ET.SubElement(user, 'pwd').text = password
    # Using placeholder for uid (this logic does not guarantee unique IDs)
    ET.SubElement(user, 'mail').text = email

    print(email)

    tree.write("users.xml")
    return render_template("index.html", message="Data added successfully")

if __name__ == "__main__":
    app.run(debug=True)
