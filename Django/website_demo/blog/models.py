from django.db import models
from django.core.exceptions import ValidationError
from django.contrib.auth.hashers import make_password, check_password
import re


def validate_username(username):
    if len(username) < 4:
        raise ValidationError("Username shall be at least 4 characters")
    if not username.isalnum():
        raise ValidationError("Username shall be alphanumeric")

def validate_pwd(pwd):
    if len(pwd) < 8 or len(pwd) > 128:
        raise ValidationError("password shall be at least 8 and not more than 128 chars")
    if not any( [c.isupper() for c in pwd]):
        raise ValidationError("Password shall have at least 1 uppercase")
    if not any( [c.islower() for c in pwd]):
        raise ValidationError("Password shall have at least 1 uppercase")
    if not re.search("[&@!]", pwd):
        raise ValidationError("pass does not have at least 1 special symbol")


# TODO: User class
class User(models.Model):
    username = models.CharField(max_length=200, unique=True, validators=[validate_username])
    password = models.TextField(validators=[validate_pwd])
    email = models.EmailField(unique=True)


# TODO: Post class
class Post(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    title = models.CharField(max_length=400)
    content = models.TextField()
