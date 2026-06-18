<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>


<form id="registerForm" action="Register" method="POST" enctype="multipart/form-data">

    <div>
        <label for="firstName" class="w3-text-theme">First name</label>
        <input class="w3-input w3-border w3-light-grey" type="text" id="firstName" name="firstName" required minlength="2" maxlength="50"
            value="${user.firstName}" title="First name must be between 2 and 50 characters." />
    </div>

    <div>
        <label for="lastName" class="w3-text-theme">Last name</label>
        <input class="w3-input w3-border w3-light-grey" type="text" id="lastName" name="lastName" required minlength="2" maxlength="50"
            value="${user.lastName}" title="Last name must be between 2 and 50 characters." />
    </div>

    <div>
        <label for="dateOfBirth" class="w3-text-theme">Date of birth</label>
        <input class="w3-input w3-border w3-light-grey" type="date" id="dateOfBirth" name="dateOfBirth" required
            value="${user.dateOfBirth}" title="Please enter a valid date." />
    </div>

    <div>
        <label for="comarca" class="w3-text-theme">Comarca d'origen</label>
        <select class="w3-input w3-border w3-light-grey" id="comarca" name="comarca">
            <option value="">-- Select a comarca --</option>
            <option value="Alt Camp" ${user.comarca == 'Alt Camp' ? 'selected' : ''}>Alt Camp</option>
            <option value="Alt Empordà" ${user.comarca == 'Alt Empordà' ? 'selected' : ''}>Alt Empordà</option>
            <option value="Alt Penedès" ${user.comarca == 'Alt Penedès' ? 'selected' : ''}>Alt Penedès</option>
            <option value="Anoia" ${user.comarca == 'Anoia' ? 'selected' : ''}>Anoia</option>
            <option value="Bages" ${user.comarca == 'Bages' ? 'selected' : ''}>Bages</option>
            <option value="Baix Camp" ${user.comarca == 'Baix Camp' ? 'selected' : ''}>Baix Camp</option>
            <option value="Baix Ebre" ${user.comarca == 'Baix Ebre' ? 'selected' : ''}>Baix Ebre</option>
            <option value="Baix Llobregat" ${user.comarca == 'Baix Llobregat' ? 'selected' : ''}>Baix Llobregat</option>
            <option value="Baix Penedès" ${user.comarca == 'Baix Penedès' ? 'selected' : ''}>Baix Penedès</option>
            <option value="Barcelona" ${user.comarca == 'Barcelona' ? 'selected' : ''}>Barcelona</option>
            <option value="Berguedà" ${user.comarca == 'Berguedà' ? 'selected' : ''}>Berguedà</option>
            <option value="Cerdanya" ${user.comarca == 'Cerdanya' ? 'selected' : ''}>Cerdanya</option>
            <option value="Conca de Barberà" ${user.comarca == 'Conca de Barberà' ? 'selected' : ''}>Conca de Barberà</option>
            <option value="Garraf" ${user.comarca == 'Garraf' ? 'selected' : ''}>Garraf</option>
            <option value="Garrigues" ${user.comarca == 'Garrigues' ? 'selected' : ''}>Garrigues</option>
            <option value="Garrotxa" ${user.comarca == 'Garrotxa' ? 'selected' : ''}>Garrotxa</option>
            <option value="Gironès" ${user.comarca == 'Gironès' ? 'selected' : ''}>Gironès</option>
            <option value="Maresme" ${user.comarca == 'Maresme' ? 'selected' : ''}>Maresme</option>
            <option value="Montsià" ${user.comarca == 'Montsià' ? 'selected' : ''}>Montsià</option>
            <option value="Noguera" ${user.comarca == 'Noguera' ? 'selected' : ''}>Noguera</option>
            <option value="Osona" ${user.comarca == 'Osona' ? 'selected' : ''}>Osona</option>
            <option value="Pallars Jussà" ${user.comarca == 'Pallars Jussà' ? 'selected' : ''}>Pallars Jussà</option>
            <option value="Pallars Sobirà" ${user.comarca == 'Pallars Sobirà' ? 'selected' : ''}>Pallars Sobirà</option>
            <option value="Pla d'Urgell" ${user.comarca == "Pla d'Urgell" ? 'selected' : ''}>Pla d'Urgell</option>
            <option value="Pla de l'Estany" ${user.comarca == "Pla de l'Estany" ? 'selected' : ''}>Pla de l'Estany</option>
            <option value="Priorat" ${user.comarca == 'Priorat' ? 'selected' : ''}>Priorat</option>
            <option value="Ribera d'Ebre" ${user.comarca == "Ribera d'Ebre" ? 'selected' : ''}>Ribera d'Ebre</option>
            <option value="Ripollès" ${user.comarca == 'Ripollès' ? 'selected' : ''}>Ripollès</option>
            <option value="Segarra" ${user.comarca == 'Segarra' ? 'selected' : ''}>Segarra</option>
            <option value="Segrià" ${user.comarca == 'Segrià' ? 'selected' : ''}>Segrià</option>
            <option value="Selva" ${user.comarca == 'Selva' ? 'selected' : ''}>Selva</option>
            <option value="Solsonès" ${user.comarca == 'Solsonès' ? 'selected' : ''}>Solsonès</option>
            <option value="Tarragonès" ${user.comarca == 'Tarragonès' ? 'selected' : ''}>Tarragonès</option>
            <option value="Terra Alta" ${user.comarca == 'Terra Alta' ? 'selected' : ''}>Terra Alta</option>
            <option value="Urgell" ${user.comarca == 'Urgell' ? 'selected' : ''}>Urgell</option>
            <option value="Vallès Occidental" ${user.comarca == 'Vallès Occidental' ? 'selected' : ''}>Vallès Occidental</option>
            <option value="Vallès Oriental" ${user.comarca == 'Vallès Oriental' ? 'selected' : ''}>Vallès Oriental</option>
        </select>
    </div>

    <div>
        <label for="email" class="w3-text-theme">Email</label>
        <input class="w3-input w3-border w3-light-grey" type="email" id="email" name="email" required
            value="${user.email}" title="Enter a valid email address." />
    </div>

    <div>
        <label for="name" class="w3-text-theme">Username</label>
        <input class="w3-input w3-border w3-light-grey" type="text" id="name" name="name" required minlength="5" maxlength="20"
            value="${user.name}" title="Username must be between 5 and 20 characters." />
    </div>

    <div>
        <label for="password" class="w3-text-theme">Password</label>
        <input class="w3-input w3-border w3-light-grey" type="password" id="password" name="password" required
            pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*]).{8,}$" value="${user.password}"
            title="Minimum 8 characters, including uppercase, numbers, and a special character (@#$%^&*)." />
    </div>

    <div>
        <label for="confirmPassword" class="w3-text-theme">Repeat password</label>
        <input class="w3-input w3-border w3-light-grey" type="password" id="confirmPassword"
            name="confirmPassword" required value="${user.password}"
            title="Passwords must match" />
    </div>

    <div>
        <label for="picture" class="w3-text-theme">Profile Picture</label>
        <input class="w3-input w3-border w3-light-grey" type="file" id="picture" name="picture" accept="image/*" />
    </div>

    <button type="submit" class="w3-button w3-theme w3-section">Submit Registration</button>

</form>


<script>
	App.Errors = {
		  <c:forEach var="error" items="${errors}">
		    "${error.key}": "${error.value}",
		  </c:forEach>
	};
	App.initRegisterValidation(App.Errors);
</script>
