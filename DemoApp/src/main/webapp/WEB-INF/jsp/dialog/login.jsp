<div id="login-dialog" title="Login" class="hidden dataForm" >
  <p class="validateTips">Enter your username and password.</p>
  <form id="login-form" name="login">
    <p>
    <label for="loginForm-name" class="width100" style="display:inline-block;">Username:</label>
    <input type="text" name="username" id="loginForm-username"
           class="text ui-widget-content ui-corner-all width150">
    </p>
    <p>
    <label for="loginForm-password" class="width100" style="display:inline-block;" >Password:</label>
    <input type="password" name="password" id="loginForm-password"
    
     class="text ui-widget-content ui-corner-all width150">
    <!-- Allow form submission with keyboard without duplicating the dialog button -->
    <input type="submit" tabindex="-1" class="hidden">
    </p>
  </form>
</div>
