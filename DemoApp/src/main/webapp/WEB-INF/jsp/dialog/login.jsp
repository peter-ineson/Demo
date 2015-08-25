<div id="login-dialog" title="Login" class="hidden">
  <p class="validateTips">Enter your username and password.</p>
  <form id="login-form" name="login">
    <fieldset>
      <label for="name">Username</label>
      <input type="text" name="username" id="username" class="text ui-widget-content ui-corner-all">
      <label for="password">Password</label>
      <input type="password" name="password" id="password" class="text ui-widget-content ui-corner-all">
      <!-- Allow form submission with keyboard without duplicating the dialog button -->
      <input type="submit" tabindex="-1" style="position:absolute; top:-1000px">
    </fieldset>
  </form>
</div>
