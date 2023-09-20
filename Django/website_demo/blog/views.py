from django.shortcuts import render
from django.shortcuts import get_object_or_404
from django.forms import ModelForm
from django.http import HttpResponseRedirect, HttpResponse
from django.urls import reverse
from django.core.exceptions import ValidationError
from django.contrib.auth.hashers import make_password, check_password

from .models import User, Post

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
            return HttpResponseRedirect(reverse("blog:login"))
        except ValidationError as e:
            return render(request, 'blog/register.html', {'errors': e})

    return render(request, 'blog/register.html')



# SECURE VERSION!
def login(request):
    if request.POST:
        username = request.POST["username"]
        pwd = request.POST["password"]
        user_list = User.objects.filter(username=username) # list
        if user_list and check_password(pwd, user_list[0].password):
            request.session["user"] = username
            request.session["id"] = user_list[0].id
            return HttpResponseRedirect(reverse("blog:list_posts"))
        else:
            errors = [('Authentication', 'Invalid credentials username/password combination does not match')]
            return render(request, 'blog/login.html', {'errors': errors})
    # if we reach in here, it means it is a GET request
    return render(request, 'blog/login.html')


# def login(request):
#     if request.POST:
#         username = request.POST["username"]
#         pwd = request.POST["password"]
#         user_list = User.objects.raw(f"SELECT * FROM blog_user WHERE username = '{username}'") # list
#         if user_list and check_password(pwd, user_list[0].password):
#             request.session["user"] = username
#             request.session["id"] = user_list[0].id
#             return HttpResponseRedirect(reverse("blog:list_posts"))
#         else:
#             errors = [('Authentication', 'Invalid credentials username/password combination does not match')]
#             return render(request, 'blog/login.html', {'errors': errors})
#     # if we reach in here, it means it is a GET request
#     return render(request, 'blog/login.html')

def logout(request):
    del request.session["user"]
    del request.session["id"]
    return HttpResponseRedirect(reverse("blog:login"))


def create_post(request):
    if request.POST:
        if request.session["user"] is None:
            errors = [('Authentication', "Not authenticated")]
            return render(request, 'blog/create.html', {'errors':errors})

        title = request.POST["title"]
        content = request.POST["content"]
        user_id = request.session["id"]
        user = User.objects.get(pk=user_id)
        post = Post(title=title, content=content, creator=user)
        post.save()
        return HttpResponseRedirect(reverse("blog:list_posts"))
    return render(request, 'blog/create.html')


def list_posts(request):
    context = {
        "posts": Post.objects.all()
    }
    return render(request, 'blog/list.html', context)


def view_post(request, post_id):
    post = get_object_or_404(Post, pk=post_id)
    return render(request, 'blog/view.html', {'post': post})
