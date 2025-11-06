package com.example.gestaoresiduos.config;

import com.example.gestaoresiduos.entity.PontoDeColeta;
import com.example.gestaoresiduos.entity.Role;
import com.example.gestaoresiduos.entity.TipoDeResiduo;
import com.example.gestaoresiduos.entity.Usuario;
import com.example.gestaoresiduos.repository.PontoDeColetaRepository;
import com.example.gestaoresiduos.repository.TipoDeResiduoRepository;
import com.example.gestaoresiduos.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
  private final UsuarioRepository usuarioRepository;
  private final TipoDeResiduoRepository tipoDeResiduoRepository;
  private final PontoDeColetaRepository pontoDeColetaRepository;
  private final PasswordEncoder passwordEncoder;

  public DataInitializer(UsuarioRepository usuarioRepository,
                         TipoDeResiduoRepository tipoDeResiduoRepository,
                         PontoDeColetaRepository pontoDeColetaRepository,
                         PasswordEncoder passwordEncoder) {
    this.usuarioRepository = usuarioRepository;
    this.tipoDeResiduoRepository = tipoDeResiduoRepository;
    this.pontoDeColetaRepository = pontoDeColetaRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void run(String... args) throws Exception {

    if (usuarioRepository.count() == 0) {
      Usuario admin = new Usuario();
      admin.setNome("Admin");
      admin.setEmail("admin@gestao.com");
      admin.setSenha(passwordEncoder.encode("admin123"));
      admin.setRole(Role.ROLE_ADMIN);

      Usuario user = new Usuario();
      user.setNome("Usuario Comum");
      user.setEmail("user@gestao.com");
      user.setSenha(passwordEncoder.encode("user123"));
      user.setRole(Role.ROLE_USER);

      Usuario coleta = new Usuario();
      coleta.setNome("Equipe Coleta");
      coleta.setEmail("coleta@gestao.com");
      coleta.setSenha(passwordEncoder.encode("coleta123"));
      coleta.setRole(Role.ROLE_COLETA);

      usuarioRepository.saveAll(List.of(admin, user, coleta));
    }

    if (tipoDeResiduoRepository.count() == 0) {
      TipoDeResiduo plastico = new TipoDeResiduo();
      plastico.setNome("Plástico");
      plastico.setInstrucoesDescarte("Lavar e secar embalagens. Garrafas, potes e sacos plásticos são recicláveis.");

      TipoDeResiduo vidro = new TipoDeResiduo();
      vidro.setNome("Vidro");
      vidro.setInstrucoesDescarte("Lavar e secar. Cuidado ao descartar vidros quebrados, embrulhe em jornal.");

      TipoDeResiduo organico = new TipoDeResiduo();
      organico.setNome("Orgânico");
      organico.setInstrucoesDescarte("Restos de alimentos, cascas de frutas, borra de café. Não misturar com lixo comum.");

      tipoDeResiduoRepository.saveAll(List.of(plastico, vidro, organico));
    }

    if (pontoDeColetaRepository.count() == 0) {
      PontoDeColeta pontoA = new PontoDeColeta();
      pontoA.setNome("Contêiner Bloco A");
      pontoA.setLocalizacao("Em frente ao Bloco A");
      pontoA.setCapacidadeMaximaKg(50.0);
      pontoA.setNivelAtualKg(0.0);

      pontoDeColetaRepository.save(pontoA);
    }
  }
}
