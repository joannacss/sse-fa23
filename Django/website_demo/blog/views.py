from django.shortcuts import render
from django.shortcuts import get_object_or_404
from django.forms import ModelForm
from django.http import HttpResponseRedirect, HttpResponse
from django.urls import reverse
from django.core.exceptions import ValidationError
from django.contrib.auth.hashers import make_password, check_password

from .models import User

def index(request):
    context = {'course': "CSE-60770", 'semester': 'FA23'}
    return render(request, 'blog/index.html', context)


def register(request):
    if request.POST:
        username = request.POST["username"]
        pwd = request.POST["password"]
        email  = request.POST["email"]
        user = User(username=username, password=pwd, email=email)
        try:
            user.full_clean()
            # all good
            user.password = make_password(pwd)
            user.save()
            return HttpResponseRedirect("/blog/login")
        except ValidationError as e:
            return render(request, 'blog/register.html', {'errors': e})

    return render(request, 'blog/register.html')



def login(request):
    if request.POST:
        username = request.POST["username"]
        pwd = request.POST["password"]
        user = User.objects.filter(username=username) # list
        if user:
            pass



    return render(request, 'blog/login.html')


def logout(request):
    pass


def create_post(request):
    pass




def list_posts(request):
    context = {
        "posts": Post.objects.all()
    }
    return render(request, 'blog/list.html', context)


def view_post(request, post_id):
    post = get_object_or_404(Post, pk=post_id)
    return render(request, 'blog/view.html', {'post': post})
