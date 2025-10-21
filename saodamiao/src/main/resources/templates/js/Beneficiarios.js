(function () {
    /*** AJUSTE AQUI SE PRECISAR ***/
    const CONTENT_ID   = 'app-content';
    const API_INSERT   = 'http://localhost:8080/beneficiarios/cadastro'; // <- sua rota de salvar
    // const API_UPDATE = 'http://localhost:8080/Beneficiarios/atualizar'; // se for usar edição

    // Estado simples (create | edit)
    let currentMode = 'create';
    let cpfOriginal = null;

    const TEMPLATE = `
    <div class="form-shell">
        <br><br>
      <div class="mb-3">
        <h3 class="mb-0" id="formTitle">
          <i class="fas fa-user me-2"></i>Pessoas • Beneficiários • Cadastrar
        </h3>
      </div>

      <div id="formErrors" class="form-errors"></div>

      <div class="card shadow-sm">
        <div class="card-body">
          <form id="formBeneficiario" name="beneficiario" class="needs-validation" novalidate>
            <div class="mb-3">
              <label for="nome" class="form-label">Nome completo</label>
              <input type="text" class="form-control form-control-lg" id="nome" name="nome" maxlength="60" required placeholder= "Nome Completo">
              <div class="invalid-feedback">Informe o nome (2 a 60 caracteres).</div>
            </div>

            <div class="mb-3">
              <label for="cpf" class="form-label">CPF</label>
              <input type="text" class="form-control form-control-lg" id="cpf" name="cpf" maxlength="14" required placeholder="000.000.000-00">
              <div class="invalid-feedback">Informe um CPF válido.</div>
            </div>

            <div class="mb-3">
              <label for="telefone" class="form-label">Telefone</label>
              <input type="text" class="form-control form-control-lg" id="telefone" name="telefone" maxlength="15" required placeholder="(00) 00000-0000">
              <div class="invalid-feedback">Formato (00) 00000-0000.</div>
            </div>

            <div class="mb-2">
              <label for="email" class="form-label">E-mail</label>
              <input type="email" class="form-control form-control-lg" id="email" name="email" maxlength="60" required placeholder="Teste@hotmail.com">
              <div class="invalid-feedback">Informe um e-mail válido.</div>
            </div>
            
            <div class="mb-3">
              <label for="cep" class="form-label">CEP</label>
              <input type="text" class="form-control form-control-lg" id="cep" name="cep"
                     maxlength="9" required inputmode="numeric" pattern="^\\d{5}-\\d{3}$" placeholder="00000-000">
              <div class="invalid-feedback">Formato 00000-000.</div>
            </div>
            
            <div class="mb-3">
              <label for="rua" class="form-label">Rua</label>
              <input type="text" class="form-control form-control-lg" id="rua" name="rua" maxlength="40" required placeholder="Nome da Rua Com Numero">
              <div class="invalid-feedback">Informe o nome da rua (2 a 60 caracteres).</div>
            </div>
            
            <div class="mb-3">
              <label for="bairro" class="form-label">Bairro</label>
              <input type="text" class="form-control form-control-lg" id="bairro" name="bairro" maxlength="40" required placeholder="Bairro">
              <div class="invalid-feedback">Informe o nome do bairro (2 a 60 caracteres).</div>
            </div>
            
            <div class="mb-3">
              <label for="cidade" class="form-label">Cidade</label>
              <input type="text" class="form-control form-control-lg" id="cidade" name="cidade" maxlength="40" required placeholder="Cidade">
              <div class="invalid-feedback">Informe o nome do cidade (2 a 60 caracteres).</div>
            </div>
            
            <div class="mb-3">
              <label for="uf" class="form-label">UF</label>
              <input type="text" class="form-control form-control-lg" id="uf" name="uf"
                     maxlength="2" required pattern="^[A-Za-z]{2}$" placeholder="SP">
              <div class="invalid-feedback">Informe a UF com 2 letras (ex.: SP).</div>
            </div>
                   
            <div class="d-flex gap-2 mt-4 justify-content-center">
              <button type="submit" class="btn btn-success" id="submitBtn">
                <i class="fas fa-save me-2"></i>Salvar
              </button>
              <button type="reset" class="btn btn-outline-secondary">
                <i class="fas fa-eraser me-2"></i>Limpar
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>`;

    function mountForm() {
        let host = document.getElementById(CONTENT_ID);
        if (!host) {
            host = document.createElement('main');
            host.id = CONTENT_ID;
            document.body.appendChild(host);
        }
        currentMode = 'create';
        cpfOriginal = null;

        host.classList.add('center-content');
        host.innerHTML = TEMPLATE;

        // título/botão
        const title = document.getElementById('formTitle');
        const btn   = document.getElementById('submitBtn');
        if (title) title.innerHTML = `<i class="fas fa-user me-2"></i>Pessoas • Beneficiários • Cadastrar`;
        if (btn)   btn.innerHTML   = `<i class="fas fa-save me-2"></i>Salvar`;

        initMasksAndValidation();
        document.body.classList.remove('sidebar_minimize'); // opcional
    }

    function initMasksAndValidation() {
        const form = document.getElementById('formBeneficiario');
        const erros = document.getElementById('formErrors');

        // máscaras
        const cpf = document.getElementById('cpf');
        if (cpf) cpf.addEventListener('input', () => {
            let v = cpf.value.replace(/\D/g, '').slice(0, 11);
            if (v.length > 9)      cpf.value = `${v.slice(0,3)}.${v.slice(3,6)}.${v.slice(6,9)}-${v.slice(9)}`;
            else if (v.length > 6) cpf.value = `${v.slice(0,3)}.${v.slice(3,6)}.${v.slice(6)}`;
            else if (v.length > 3) cpf.value = `${v.slice(0,3)}.${v.slice(3)}`;
            else                   cpf.value = v;
        });

        function onlyDigits (s) { return String(s ?? '').replace(/\D/g, ''); }

        const tel = document.getElementById('telefone');
        if (tel) tel.addEventListener('input', () => {
            let v = tel.value.replace(/\D/g, '').slice(0, 11);
            if (v.length > 10)      tel.value = `(${v.slice(0,2)}) ${v.slice(2,7)}-${v.slice(7)}`;
            else if (v.length > 6)  tel.value = `(${v.slice(0,2)}) ${v.slice(2,6)}-${v.slice(6)}`;
            else if (v.length > 2)  tel.value = `(${v.slice(0,2)}) ${v.slice(2)}`;
            else                    tel.value = v;
        });

        // submit
        form.addEventListener('submit', async (e) => {
            e.preventDefault();

            if (!form.checkValidity()) {
                form.classList.add('was-validated');
                return;
            }

            const dto = {
                nome:     document.getElementById('nome').value.trim(),
                cpf:      onlyDigits(document.getElementById('cpf').value),
                telefone: onlyDigits(document.getElementById('telefone').value), // <-- aqui era cpf
                email:    document.getElementById('email').value.trim(),
                data_cadastro: new Date().toISOString().slice(0, 10), // "yyyy-MM-dd" data no formato ISO (Jackson entende melhor) ou deixe o back preencher
                cep:      onlyDigits(document.getElementById('cep').value),
                endereco: document.getElementById('rua').value.trim(),
                bairro:   document.getElementById('bairro').value.trim(),
                cidade:   document.getElementById('cidade').value.trim(),
                uf:       document.getElementById('uf').value.toUpperCase()
            };

            // validações extras
            const msgs = [];
            if (dto.nome.length < 2) msgs.push('Nome muito curto.');
            if (!/^\d{11}$/.test(dto.cpf)) msgs.push('CPF deve ter 11 dígitos.');
            if (!/^\d{10,11}$/.test(dto.telefone)) msgs.push('Telefone inválido (10 ou 11 dígitos).');
            if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(dto.email)) msgs.push('E-mail inválido.');
            if (msgs.length) { showErrors(msgs); return; }
            console.log(dto);
            toggleLoading(true);
            try {
                const resp = await fetch(API_INSERT, {
                    method: currentMode === 'edit' ? 'PUT' : 'POST',
                    headers: { 'Content-Type': 'application/json', 'Accept': 'application/json' },
                    body: JSON.stringify(dto)
                });

                if (!resp.ok) {
                    const msg = await readBody(resp);
                    showErrors([`Erro ao salvar (${resp.status}): ${msg}`]);
                    return;
                }

                await safeJson(resp).catch(() => ({}));
                alert('Beneficiário salvo com sucesso!');
                form.reset();
                form.classList.remove('was-validated');
                currentMode = 'create';
                cpfOriginal = null;
            } catch (err) {
                showErrors(['Falha de rede ao salvar.']);
                console.error(err);
            } finally {
                toggleLoading(false);
            }

            function showErrors(list) {
                if (!erros) { alert(list.join('\n')); return; }
                erros.innerHTML = list.map(m => `<div class="alert alert-danger mb-2">${escapeHtml(m)}</div>`).join('');
            }
            function toggleLoading(on) {
                const btn = document.getElementById('submitBtn');
                if (!btn) return;
                if (on) {
                    btn.disabled = true;
                    btn.dataset._old = btn.innerHTML;
                    btn.innerHTML = `<span class="spinner-border spinner-border-sm me-2"></span>Salvando...`;
                } else {
                    btn.disabled = false;
                    if (btn.dataset._old) btn.innerHTML = btn.dataset._old;
                }
            }
            async function readBody(resp){
                const ct = resp.headers.get('content-type') || '';
                try{
                    if (ct.includes('application/json')) {
                        const j = await resp.json();
                        return j?.mensagem || j?.error || JSON.stringify(j);
                    }
                    return await resp.text();
                } catch { return ''; }
            }
            async function safeJson(resp){ return resp.json(); }
            function escapeHtml(s){ return String(s||'').replace(/[&<>"']/g,c=>({ '&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#039;' }[c])); }
        });
    }

    // API público pra você chamar no HTML: onclick="return CrudBeneficiario.mount();"
    window.CrudBeneficiario = {
        mount: () => { mountForm(); return false; }
        // você pode adicionar .unmount() e .list() depois
    };

    // se quiser montar automaticamente quando o script carregar, descomente:
    // document.addEventListener('DOMContentLoaded', () => CrudBeneficiario.mount());
})();